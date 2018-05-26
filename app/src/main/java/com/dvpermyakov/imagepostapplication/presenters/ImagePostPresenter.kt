package com.dvpermyakov.imagepostapplication.presenters

import android.Manifest
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dvpermyakov.base.extensions.isPermissionGranted
import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.interactors.GalleryImageInteractor
import com.dvpermyakov.imagepostapplication.models.*
import com.dvpermyakov.imagepostapplication.views.ImagePostView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@InjectViewState
class ImagePostPresenter @Inject constructor(
        private val resources: Resources,
        private val contextHolder: IApplicationContextHolder,
        private val galleryImageInteractor: GalleryImageInteractor) : MvpPresenter<ImagePostView>() {
    private val compositeDisposable = CompositeDisposable()

    private var covers = CoverModel.getDefaults(contextHolder.getContext())
            .map {
                SelectableCoverModel(it, false)
            }
            .also {
                it.first().selected = true
            }
            .toMutableList()

    private var textAppearance = TextAppearanceModel()
    private var stickers = mutableListOf<StickerUiModel>()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val selectedCover = getSelectedCover()
        viewState.updateTextPostAppearance(selectedCover, textAppearance)
        viewState.updateImagePostAppearance(selectedCover)
        viewState.setCoverList(covers)
    }

    override fun destroyView(view: ImagePostView) {
        compositeDisposable.clear()
        super.destroyView(view)
    }

    fun onCoverItemClick(selectedItem: SelectableCoverModel) {
        setSelectedCover(selectedItem)
    }

    fun onAddCoverClick() {
        if (contextHolder.getContext().isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            viewState.openImageFromGallery()
        } else {
            viewState.showReadPermissionDialog()
        }
    }

    fun onFontClick() {
        textAppearance.nextBackground()
        viewState.updateTextPostAppearance(getSelectedCover(), textAppearance)
    }

    fun onStickerButtonClick() {
        viewState.showStickerList()
    }

    fun onPostImageClick() {
        viewState.showKeyboard()
    }

    fun onSaveClick(bitmap: Bitmap?) {
        if (contextHolder.getContext().isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (bitmap != null) {
                compositeDisposable.add(galleryImageInteractor.saveImage(bitmap, resources.getString(R.string.app_image_post_default_image_title), resources.getString(R.string.app_image_post_default_image_description))
                        .subscribeOn(Schedulers.io())
                        .delay(DIALOGS_DELAY_MS, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            viewState.showSaveImageLoadingDialog()
                        }
                        .doFinally {
                            bitmap.recycle()
                            viewState.hideSaveImageLoadingDialog()
                        }
                        .subscribe({
                            viewState.showSaveImageSuccess()
                        }, {
                            viewState.showSaveImageFailure()
                        }))
            } else {
                viewState.showSaveImageFailure()
            }
        } else {
            viewState.showWritePermissionDialog()
        }
    }

    fun onImagePick(uri: Uri) {
        compositeDisposable.add(galleryImageInteractor.getImagePath(uri)
                .subscribeOn(Schedulers.io())
                .delay(DIALOGS_DELAY_MS, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewState.showImageLoadingDialog()
                }
                .doFinally {
                    viewState.hideImageLoadingDialog()
                }
                .subscribe({ imagePath ->
                    val imageCover = SelectableCoverModel(FileCoverModel(imagePath), false)
                    val imageCoverIndex = covers.indexOfFirst { it.cover == imageCover.cover }
                    if (imageCoverIndex == -1 && covers.add(imageCover)) {
                        viewState.notifyCoverItemAdded(covers.lastIndex)
                        setSelectedCover(imageCover)
                    } else {
                        viewState.showImageIsAddedError()
                        viewState.scrollToCoverItem(imageCoverIndex)
                        setSelectedCover(covers[imageCoverIndex])
                    }
                }, {
                    viewState.showImageLoadingError()
                }))
    }

    fun onStickerAdd(sticker: StickerModel) {
        val stickerUi = StickerUiModel(resources.getDimensionPixelOffset(R.dimen.app_sticker_size), sticker)
        stickers.add(stickerUi)
        viewState.addSticker(stickerUi)
    }

    fun onStickerRemove(stickerUi: StickerUiModel) {
        stickers.remove(stickerUi)
    }

    fun onViewSizeChange() {
        viewState.updateTrashAppearance(getSelectedCover())
    }

    fun onStickerPositionChange(stickerUi: StickerUiModel, isInsideParent: Boolean, isRemovable: Boolean, isIntersectedByTrash: Boolean) {
        onStickerStateChange(stickerUi, true, isInsideParent, isRemovable, isIntersectedByTrash)
    }

    fun onStickerStateChange(stickerUi: StickerUiModel, isInMotion: Boolean, isInsideParent: Boolean, isRemovable: Boolean, isIntersectedByTrash: Boolean) {
        when {
            !isRemovable || !isInMotion -> viewState.hideTrash(stickerUi)
            isInsideParent && !isIntersectedByTrash -> viewState.showClosedTrash(stickerUi)
            else -> viewState.showOpenedTrash(stickerUi)
        }
    }

    private fun getSelectedCover() = covers.first { it.selected }.cover

    private fun setSelectedCover(selectedCover: SelectableCoverModel) {
        if (!selectedCover.selected) {
            covers.forEachIndexed { index, item ->
                if (item.selected) {
                    item.selected = false
                    viewState.notifyCoverItemChanged(index)
                } else if (item.cover == selectedCover.cover) {
                    item.selected = true
                    viewState.notifyCoverItemChanged(index)
                    viewState.updateTextPostAppearance(item.cover, textAppearance)
                    viewState.updateImagePostAppearance(item.cover)
                }
            }
        }
    }

    companion object {
        private const val DIALOGS_DELAY_MS = 4000L
    }
}
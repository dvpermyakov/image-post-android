package com.dvpermyakov.imagepostapplication.presenters

import android.Manifest
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.dvpermyakov.base.extensions.isPermissionGranted
import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.interactors.GalleryImageInteractor
import com.dvpermyakov.imagepostapplication.models.*
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter @Inject constructor(
        private val resources: Resources,
        private val contextHolder: IApplicationContextHolder,
        private val galleryImageInteractor: GalleryImageInteractor) : BaseFragmentPresenter<CreateImagePostView>() {
    private val compositeDisposable = CompositeDisposable()

    private var covers = CoverModel.getDefaults(contextHolder.getContext())
            .map {
                SelectableCoverModel(it, false)
            }
            .apply {
                first().selected = true
            }
            .toMutableList()

    private var textAppearance = TextAppearanceModel()
    private var stickers = mutableListOf<StickerUiModel>()

    override fun attachView(v: CreateImagePostView, state: Bundle?) {
        super.attachView(v, state)
        state?.let {
            covers = it.getParcelableArrayList(KEY_COVERS)
            textAppearance = it.getParcelable(KEY_TEXT_APPEARANCE)
            stickers = it.getParcelableArrayList(KEY_STICKERS)
        }
        v.setCoverList(covers)
        val selectedCover = getSelectedCover()
        view?.updateTextPostAppearance(selectedCover, textAppearance)
        view?.updateImagePostAppearance(selectedCover)
        stickers.forEach {
            view?.addSticker(it)
        }
    }

    override fun onPause() {
        view?.hideImageLoadingDialog()
        view?.hideSaveImageLoadingDialog()
        super.onPause()
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun detachView() {
        compositeDisposable.dispose()
        super.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_COVERS, ArrayList(covers))
        outState.putParcelable(KEY_TEXT_APPEARANCE, textAppearance)
        outState.putParcelableArrayList(KEY_STICKERS, ArrayList(stickers))
        super.onSaveInstanceState(outState)
    }

    fun onCoverItemClick(selectedItem: SelectableCoverModel) {
        setSelectedCover(selectedItem)
    }

    fun onAddCoverClick() {
        if (contextHolder.getContext().isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            view?.openImageFromGallery()
        } else {
            view?.showReadPermissionDialog()
        }
    }

    fun onFontClick() {
        textAppearance.nextBackground()
        view?.updateTextPostAppearance(getSelectedCover(), textAppearance)
    }

    fun onStickerButtonClick() {
        view?.showStickerList()
    }

    fun onPostImageClick() {
        view?.showKeyboard()
    }

    fun onSaveClick(bitmap: Bitmap?) {
        if (contextHolder.getContext().isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (bitmap != null) {
                compositeDisposable.add(galleryImageInteractor.saveImage(bitmap, resources.getString(R.string.app_image_post_default_image_title), resources.getString(R.string.app_image_post_default_image_description))
                        .subscribeOn(Schedulers.io())
                        .delay(DIALOGS_DELAY_MS, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            view?.showSaveImageLoadingDialog()
                        }
                        .doFinally {
                            view?.hideSaveImageLoadingDialog()
                            bitmap.recycle()
                        }
                        .subscribe({
                            view?.showSaveImageSuccess()
                        }, {
                            view?.showSaveImageFailure()
                        }))
            } else {
                view?.showSaveImageFailure()
            }
        } else {
            view?.showWritePermissionDialog()
        }
    }

    fun onImagePick(uri: Uri) {
        compositeDisposable.add(galleryImageInteractor.getImagePath(uri)
                .subscribeOn(Schedulers.io())
                .delay(DIALOGS_DELAY_MS, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view?.showImageLoadingDialog()
                }
                .doFinally {
                    view?.hideImageLoadingDialog()
                }
                .subscribe({ imagePath ->
                    val imageCover = SelectableCoverModel(FileCoverModel(imagePath), false)
                    val imageCoverIndex = covers.indexOfFirst { it.cover == imageCover.cover }
                    if (imageCoverIndex == -1 && covers.add(imageCover)) {
                        view?.notifyCoverItemAdded(covers.lastIndex)
                        setSelectedCover(imageCover)
                    } else {
                        view?.showImageIsAddedError()
                        view?.scrollToCoverItem(imageCoverIndex)
                        setSelectedCover(covers[imageCoverIndex])
                    }
                }, {
                    view?.showImageLoadingError()
                }))
    }

    fun onStickerAdd(sticker: StickerModel) {
        val stickerUi = StickerUiModel(resources.getDimensionPixelOffset(R.dimen.app_sticker_size), sticker)
        stickers.add(stickerUi)
        view?.addSticker(stickerUi)
    }

    fun onStickerRemove(stickerUi: StickerUiModel) {
        stickers.remove(stickerUi)
    }

    fun onViewSizeChange() {
        view?.updateTrashAppearance(getSelectedCover())
    }

    fun onStickerPositionChange(stickerUi: StickerUiModel, isInsideParent: Boolean, isRemovable: Boolean, isIntersectedByTrash: Boolean) {
        onStickerStateChange(stickerUi, true, isInsideParent, isRemovable, isIntersectedByTrash)
    }

    fun onStickerStateChange(stickerUi: StickerUiModel, isInMotion: Boolean, isInsideParent: Boolean, isRemovable: Boolean, isIntersectedByTrash: Boolean) {
        when {
            !isRemovable || !isInMotion -> view?.hideTrash(stickerUi)
            isInsideParent && !isIntersectedByTrash -> view?.showClosedTrash(stickerUi)
            else -> view?.showOpenedTrash(stickerUi)
        }
    }

    private fun getSelectedCover() = covers.first { it.selected }.cover

    private fun setSelectedCover(selectedCover: SelectableCoverModel) {
        if (!selectedCover.selected) {
            covers.forEachIndexed { index, item ->
                if (item.selected) {
                    item.selected = false
                    view?.notifyCoverItemChanged(index)
                } else if (item.cover == selectedCover.cover) {
                    item.selected = true
                    view?.notifyCoverItemChanged(index)
                    view?.updateTextPostAppearance(item.cover, textAppearance)
                    view?.updateImagePostAppearance(item.cover)
                }
            }
        }
    }

    companion object {
        private const val KEY_COVERS = "covers"
        private const val KEY_TEXT_APPEARANCE = "textAppearance"
        private const val KEY_STICKERS = "stickers"

        private const val DIALOGS_DELAY_MS = 400L
    }
}
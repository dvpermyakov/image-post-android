package com.dvpermyakov.imagepostapplication.presenters

import android.Manifest
import android.net.Uri
import android.os.Bundle
import com.dvpermyakov.base.extensions.isPermissionGranted
import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.interactors.GalleryImageInteractor
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.models.FileCoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.models.TextAppearanceModel
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter @Inject constructor(
        private val contextHolder: IApplicationContextHolder,
        private val galleryImageInteractor: GalleryImageInteractor) : BaseFragmentPresenter<CreateImagePostView>() {
    private val compositeDisposable = CompositeDisposable()

    private var covers = CoverModel.getDefaults(contextHolder.getContext())
            .map {
                SelectableCoverModel(it, false)
            }
            .toMutableList()
            .apply {
                first().selected = true
            }

    private var textAppearance = TextAppearanceModel()

    override fun attachView(v: CreateImagePostView, state: Bundle?) {
        super.attachView(v, state)
        state?.let {
            covers = it.getParcelableArrayList(KEY_COVERS)
            textAppearance = it.getParcelable(KEY_TEXT_APPEARANCE)
        }
        v.setCoverList(covers)
        view?.updatePostAppearance(getSelectedCover(), textAppearance)
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
        super.onSaveInstanceState(outState)
    }

    fun onCoverItemClick(selectedItem: SelectableCoverModel) {
        if (!selectedItem.selected) {
            covers.forEachIndexed { index, item ->
                if (item.selected) {
                    item.selected = false
                    view?.notifyCoverItemChanged(index)
                } else if (item.cover == selectedItem.cover) {
                    item.selected = true
                    view?.notifyCoverItemChanged(index)
                    view?.updatePostAppearance(item.cover, textAppearance)
                }
            }
        }
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
        view?.updatePostAppearance(getSelectedCover(), textAppearance)
    }

    fun onStickerButtonClick() {
        view?.showStickerList()
    }

    fun onImagePick(uri: Uri) {
        compositeDisposable.add(galleryImageInteractor.getImagePath(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view?.showImageLoadingDialog()
                }
                .doFinally {
                    view?.hideImageLoadingDialog()
                }
                .subscribe({ imagePath ->
                    if (covers.add(SelectableCoverModel(FileCoverModel(imagePath), false))) {
                        view?.notifyCoverItemAdded(covers.lastIndex)
                    }
                }, {
                    view?.showImageLoadingError()
                }))
    }

    private fun getSelectedCover() = covers.first { it.selected }.cover

    companion object {
        private const val KEY_COVERS = "covers"
        private const val KEY_TEXT_APPEARANCE = "textAppearance"
    }
}
package com.dvpermyakov.imagepostapplication.views

import com.dvpermyakov.imagepostapplication.models.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface CreateImagePostView {
    fun setCoverList(items: List<SelectableCoverModel>)
    fun notifyCoverItemChanged(position: Int)
    fun notifyCoverItemAdded(position: Int)
    fun showStickerList()
    fun addSticker(stickerUi: StickerUiModel)
    fun showKeyboard()
    fun updateTextPostAppearance(cover: CoverModel, textAppearance: TextAppearanceModel)
    fun updateImagePostAppearance(cover: CoverModel)
    fun updateTrashAppearance(cover: CoverModel)
    fun openImageFromGallery()
    fun showReadPermissionDialog()
    fun showWritePermissionDialog()
    fun showImageLoadingDialog()
    fun hideImageLoadingDialog()
    fun showImageLoadingError()
    fun showSaveImageSuccess()
    fun showSaveImageFailure()
}
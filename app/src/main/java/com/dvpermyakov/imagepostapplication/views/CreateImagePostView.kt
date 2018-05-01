package com.dvpermyakov.imagepostapplication.views

import com.dvpermyakov.imagepostapplication.models.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface CreateImagePostView {
    fun notifyCoverItemChanged(position: Int)
    fun notifyCoverItemAdded(position: Int)
    fun showStickerList()
    fun setCoverList(items: List<SelectableCoverModel>)
    fun updateImagePostAppearance(cover: CoverModel)
    fun updateTextPostAppearance(cover: CoverModel, textAppearance: TextAppearanceModel)
    fun openImageFromGallery()
    fun showImageLoadingDialog()
    fun hideImageLoadingDialog()
    fun showImageLoadingError()
    fun showReadPermissionDialog()
    fun addSticker(stickerUi: StickerUiModel)
}
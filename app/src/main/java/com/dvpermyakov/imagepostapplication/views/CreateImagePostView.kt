package com.dvpermyakov.imagepostapplication.views

import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.models.TextAppearanceModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface CreateImagePostView {
    fun notifyCoverItemChanged(position: Int)
    fun notifyCoverItemAdded(position: Int)
    fun showStickerList()
    fun setCoverList(items: List<SelectableCoverModel>)
    fun updatePostAppearance(cover: CoverModel, textAppearance: TextAppearanceModel)
    fun openImageFromGallery()
    fun showImageLoadingDialog()
    fun hideImageLoadingDialog()
    fun showImageLoadingError()
    fun showReadPermissionDialog()
}
package com.dvpermyakov.imagepostapplication.views

import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface CreateImagePostView {
    fun notifyCoverItemChanged(position: Int)
    fun showStickerList()
    fun setCovers(items: List<SelectableCoverModel>)
}
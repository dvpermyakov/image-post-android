package com.dvpermyakov.imagepostapplication.views

import com.dvpermyakov.imagepostapplication.models.CoverModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface CreateImagePostView {
    fun showStickerList()
    fun setCovers(items: List<CoverModel>)
}
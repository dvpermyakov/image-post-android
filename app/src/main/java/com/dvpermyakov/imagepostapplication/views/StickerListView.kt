package com.dvpermyakov.imagepostapplication.views

import com.arellomobile.mvp.MvpView
import com.dvpermyakov.imagepostapplication.models.StickerModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface StickerListView : MvpView {
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun showStickers(list: List<StickerModel>)
    fun showPreviousScreen()
    fun sendChosenSticker(sticker: StickerModel)
}
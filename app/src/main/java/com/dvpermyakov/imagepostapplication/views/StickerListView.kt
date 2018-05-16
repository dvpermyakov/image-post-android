package com.dvpermyakov.imagepostapplication.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.dvpermyakov.imagepostapplication.models.StickerModel

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@StateStrategyType(AddToEndStrategy::class)
interface StickerListView : MvpView {
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun showStickers(list: List<StickerModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun sendChosenSticker(sticker: StickerModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPreviousScreen()
}
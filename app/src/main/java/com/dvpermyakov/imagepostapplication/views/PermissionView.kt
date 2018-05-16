package com.dvpermyakov.imagepostapplication.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

@StateStrategyType(AddToEndStrategy::class)
interface PermissionView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPreviousScreen()
}
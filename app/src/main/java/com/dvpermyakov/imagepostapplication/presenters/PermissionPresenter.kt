package com.dvpermyakov.imagepostapplication.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dvpermyakov.imagepostapplication.views.PermissionView

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

@InjectViewState
class PermissionPresenter : MvpPresenter<PermissionView>() {
    fun onCancelClick() {
        viewState.showPreviousScreen()
    }
}
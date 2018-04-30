package com.dvpermyakov.imagepostapplication.presenters

import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.views.PermissionView

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

class PermissionPresenter : BaseFragmentPresenter<PermissionView>() {
    fun onCancelClick() {
        view?.showPreviousScreen()
    }
}
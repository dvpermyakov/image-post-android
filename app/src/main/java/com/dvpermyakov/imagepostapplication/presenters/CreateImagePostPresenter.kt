package com.dvpermyakov.imagepostapplication.presenters

import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter : BaseFragmentPresenter<CreateImagePostView>() {
    fun onStickerButtonClick() {
        view?.showStickerList()
    }
}
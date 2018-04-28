package com.dvpermyakov.imagepostapplication.presenters

import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter : BaseFragmentPresenter<CreateImagePostView>() {
    private val covers = CoverModel.getDefault()

    override fun attachView(v: CreateImagePostView) {
        super.attachView(v)
        v.setCovers(covers)
    }

    fun onStickerButtonClick() {
        view?.showStickerList()
    }
}
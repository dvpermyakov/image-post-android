package com.dvpermyakov.imagepostapplication.presenters

import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter @Inject constructor(
        contextHolder: IApplicationContextHolder) : BaseFragmentPresenter<CreateImagePostView>() {

    private val covers = CoverModel.getDefaults(contextHolder.getContext())

    override fun attachView(v: CreateImagePostView) {
        super.attachView(v)
        v.setCovers(covers)
    }

    fun onStickerButtonClick() {
        view?.showStickerList()
    }
}
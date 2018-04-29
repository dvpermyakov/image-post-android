package com.dvpermyakov.imagepostapplication.presenters

import android.os.Bundle
import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import com.dvpermyakov.base.presenters.BaseFragmentPresenter
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostPresenter @Inject constructor(
        contextHolder: IApplicationContextHolder) : BaseFragmentPresenter<CreateImagePostView>() {

    private var covers = CoverModel.getDefaults(contextHolder.getContext()).map { SelectableCoverModel(it, false) }

    override fun attachView(v: CreateImagePostView, state: Bundle?) {
        super.attachView(v, state)
        state?.let { covers = it.getParcelableArrayList(KEY_COVERS) }
        v.setCovers(covers)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_COVERS, ArrayList(covers))
        super.onSaveInstanceState(outState)
    }

    fun onCoverItemClick(selectedItem: SelectableCoverModel) {
        if (!selectedItem.selected) {
            covers.forEachIndexed { index, item ->
                if (item.selected) {
                    item.selected = false
                    view?.notifyCoverItemChanged(index)
                } else if (item.cover == selectedItem.cover) {
                    item.selected = true
                    view?.notifyCoverItemChanged(index)
                }
            }
        }
    }

    fun onStickerButtonClick() {
        view?.showStickerList()
    }

    companion object {
        private const val KEY_COVERS = "covers"
    }
}
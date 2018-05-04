package com.dvpermyakov.imagepostapplication.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dvpermyakov.base.extensions.setVisible
import com.dvpermyakov.base.fragments.BaseMvpFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.adapters.StickerAdapter
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.dvpermyakov.imagepostapplication.presenters.StickerListPresenter
import com.dvpermyakov.imagepostapplication.utils.ImagePostApplicationConstants
import com.dvpermyakov.imagepostapplication.views.StickerListView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.michaelrocks.lightsaber.getInstance
import kotlinx.android.synthetic.main.fragment_sticker_list.*
import kotlinx.android.synthetic.main.layout_sticker_list.*


/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class StickerListFragment : BaseMvpFragment<StickerListView, StickerListPresenter>(), StickerListView {
    private val adapter by lazy {
        StickerAdapter().apply {
            clickListener = { presenter.onStickerClick(it) }
        }
    }

    override val baseView = this
    override val contentResId = R.layout.fragment_sticker_list

    override fun createPresenter(): StickerListPresenter = getApplicationInjector().getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyContainer.setOnClickListener {
            presenter.onEmptyClick()
        }

        val layoutManager = object : FlexboxLayoutManager(context) {
            override fun computeVerticalScrollOffset(state: RecyclerView.State?): Int {
                val offset = super.computeVerticalScrollOffset(state)
                dividerView?.alpha = offset / DIVIDER_ALPHA_HEIGHT
                return offset
            }
        }.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_AROUND
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun showLoading() {
        progressBarView.setVisible(true)
        recyclerView.setVisible(false)
        errorTextView.setVisible(false)
    }

    override fun hideLoading() {
        progressBarView.setVisible(false)
        recyclerView.setVisible(true)
        errorTextView.setVisible(false)
    }

    override fun showError() {
        progressBarView.setVisible(false)
        recyclerView.setVisible(false)
        errorTextView.setVisible(true)
    }

    override fun showStickers(list: List<StickerModel>) {
        adapter.items = list
    }

    override fun showPreviousScreen() {
        baseActivity.onBackPressed()
    }

    override fun sendChosenSticker(sticker: StickerModel) {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent().apply {
            putExtra(ImagePostApplicationConstants.INTENT_EXTRA_STICKER_MODEL, sticker)
        })
    }

    companion object {
        private const val DIVIDER_ALPHA_HEIGHT = 300f

        fun newInstance() = StickerListFragment()
    }
}
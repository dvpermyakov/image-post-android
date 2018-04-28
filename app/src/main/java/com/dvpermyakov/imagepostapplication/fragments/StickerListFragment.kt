package com.dvpermyakov.imagepostapplication.fragments

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.dvpermyakov.base.extensions.setVisible
import com.dvpermyakov.base.fragments.BaseMvpFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.adapters.StickerAdapter
import com.dvpermyakov.imagepostapplication.models.StickerModel
import com.dvpermyakov.imagepostapplication.presenters.StickerListPresenter
import com.dvpermyakov.imagepostapplication.views.StickerListView
import io.michaelrocks.lightsaber.getInstance
import kotlinx.android.synthetic.main.fragment_sticker_list.*

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

    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from<View>(mainContainerView) }

    override fun createPresenter(): StickerListPresenter = injector?.getInstance()!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyContainer.setOnClickListener {
            presenter.onEmptyClick()
        }

        mainContainerView.setOnClickListener { }  // to prevent clicking on empty container

        recyclerView.layoutManager = GridLayoutManager(context, STICKERS_LIST_SPAN_COUNT)
        recyclerView.adapter = adapter

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    showPreviousScreen()
                }
            }
        })
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

    companion object {
        private const val STICKERS_LIST_SPAN_COUNT = 4

        fun newInstance() = StickerListFragment()
    }
}
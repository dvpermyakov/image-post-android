package com.dvpermyakov.imagepostapplication.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dvpermyakov.base.extensions.hideKeyboard
import com.dvpermyakov.base.fragments.BaseMvpFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.adapters.CoverAdapter
import com.dvpermyakov.imagepostapplication.models.CoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.presenters.CreateImagePostPresenter
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.michaelrocks.lightsaber.getInstance
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_image_post.*
import kotlinx.android.synthetic.main.layout_image_post_header.*
import kotlinx.android.synthetic.main.layout_post.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostFragment : BaseMvpFragment<CreateImagePostView, CreateImagePostPresenter>(), CreateImagePostView {
    private val compositeDisposable = CompositeDisposable()
    private val adapter by lazy {
        CoverAdapter().apply {
            itemClickListener = { presenter.onCoverItemClick(it) }
        }
    }

    override val baseView = this
    override val contentResId = R.layout.fragment_image_post

    override fun createPresenter(): CreateImagePostPresenter = getApplicationInjector().getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stickerButtonView.setOnClickListener {
            presenter.onStickerButtonClick()
        }

        compositeDisposable.add(RxTextView.textChanges(editTextView).subscribe { text ->
            saveButtonView.isEnabled = text.isNotEmpty()
        })

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    override fun onStop() {
        baseActivity.hideKeyboard()
        super.onStop()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun showStickerList() {
        editTextView.clearFocus()
        baseActivity.hideKeyboard()
        baseActivity.addFragment(StickerListFragment.newInstance())
    }

    override fun setCovers(items: List<SelectableCoverModel>) {
        adapter.items = items
    }

    override fun notifyCoverItemChanged(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun setPostCover(cover: CoverModel) {
        coverView.cover = cover
    }

    companion object {
        fun newInstance() = CreateImagePostFragment()
    }
}
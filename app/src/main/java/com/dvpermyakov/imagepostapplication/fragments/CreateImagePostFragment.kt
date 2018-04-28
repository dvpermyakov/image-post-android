package com.dvpermyakov.imagepostapplication.fragments

import android.os.Bundle
import android.view.View
import com.dvpermyakov.base.extensions.hideKeyboard
import com.dvpermyakov.base.fragments.BaseMvpFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.presenters.CreateImagePostPresenter
import com.dvpermyakov.imagepostapplication.views.CreateImagePostView
import kotlinx.android.synthetic.main.layout_image_post_header.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostFragment : BaseMvpFragment<CreateImagePostView, CreateImagePostPresenter>(), CreateImagePostView {
    override val baseView = this
    override val contentResId = R.layout.fragment_image_post

    override fun createPresenter() = CreateImagePostPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stickerButtonView.setOnClickListener {
            presenter.onStickerButtonClick()
        }
    }

    override fun onStop() {
        baseActivity.hideKeyboard()
        super.onStop()
    }

    override fun showStickerList() {
        baseActivity.hideKeyboard()
        baseActivity.addFragment(StickerListFragment.newInstance())
    }

    companion object {
        fun newInstance() = CreateImagePostFragment()
    }
}
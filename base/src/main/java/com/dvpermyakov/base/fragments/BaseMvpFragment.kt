package com.dvpermyakov.base.fragments

import android.os.Bundle
import com.dvpermyakov.base.presenters.BaseFragmentPresenter

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseMvpFragment<V, out P : BaseFragmentPresenter<V>> : BaseFragment() {
    protected val presenter: P by lazy { createPresenter() }

    protected abstract val baseView: V

    protected abstract fun createPresenter(): P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(baseView)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            presenter.onViewStateRestored(it)
        }
    }
}
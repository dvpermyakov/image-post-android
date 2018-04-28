package com.dvpermyakov.base.presenters

import android.os.Bundle

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseFragmentPresenter<V> {
    protected var view: V? = null

    open fun attachView(v: V) {
        view = v
    }

    open fun onStart() {

    }

    open fun onResume() {

    }

    open fun onPause() {

    }

    open fun onStop() {

    }

    open fun onSaveInstanceState(outState: Bundle) {

    }

    open fun onViewStateRestored(savedInstanceState: Bundle) {

    }

    open fun detachView() {
        view = null
    }
}
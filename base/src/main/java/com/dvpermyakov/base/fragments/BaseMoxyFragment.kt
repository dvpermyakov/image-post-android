package com.dvpermyakov.base.fragments

import com.arellomobile.mvp.MvpAppCompatFragment
import com.dvpermyakov.base.activities.BaseActivity
import io.michaelrocks.lightsaber.Injector

abstract class BaseMoxyFragment : MvpAppCompatFragment() {
    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

    protected val appInjector: Injector
        get() = baseActivity.getApplicationInjector()
}
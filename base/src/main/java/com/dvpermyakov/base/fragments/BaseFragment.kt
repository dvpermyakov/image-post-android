package com.dvpermyakov.base.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.base.ioc.IEnrichableItem
import com.dvpermyakov.base.ioc.IInjectorHolder
import io.michaelrocks.lightsaber.Injector

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseFragment : Fragment(), IEnrichableItem {
    private var injector: Injector? = null

    protected val baseActivity
        get() = activity as BaseActivity

    protected abstract val contentResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentResId, container, false)
    }

    override fun bindInjector(holder: IInjectorHolder) {
        injector = holder.getInjector()
    }

    override fun unbindInjector() {
        injector = null
    }

    fun getApplicationInjector(): Injector {
        injector?.let { injector ->
            return injector
        } ?: throw IllegalArgumentException("cannot get injector before its binding or after its unbinding")
    }
}
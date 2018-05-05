package com.dvpermyakov.base.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dvpermyakov.base.R
import com.dvpermyakov.base.ioc.IEnrichableItem
import com.dvpermyakov.base.ioc.IInjectorHolder
import com.dvpermyakov.base.models.AnimationConfig
import io.michaelrocks.lightsaber.Injector

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseActivity : AppCompatActivity(), IEnrichableItem {
    private var injector: Injector? = null

    protected open val layoutId = R.layout.activity_base

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (savedInstanceState == null) {
            replaceFragment(createFragment(), false)
        }
    }

    override fun bindInjector(holder: IInjectorHolder) {
        injector = holder.getInjector()
    }

    override fun unbindInjector() {
        injector = null
    }

    fun replaceFragment(fragment: Fragment, withBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            if (withBackStack) {
                addToBackStack(geFragmentBackStackName(fragment))
            }
        }.commit()
    }

    fun addFragment(fragment: Fragment, tag: String = "", animConfig: AnimationConfig? = AnimationConfig.getBottomAnimationConfig()) {
        val transaction = supportFragmentManager.beginTransaction()
        animConfig?.let {
            transaction.setCustomAnimations(it.enter, it.exit, it.enterPop, it.exitPop)
        }
        if (tag.isNotEmpty()) {
            transaction.add(R.id.fragment_container, fragment, tag)
        } else {
            transaction.add(R.id.fragment_container, fragment)
        }
        transaction
                .addToBackStack(geFragmentBackStackName(fragment))
                .commit()
    }

    fun getApplicationInjector(): Injector {
        injector?.let { injector ->
            return injector
        } ?: throw IllegalArgumentException("cannot get injector before its binding or after its unbinding")
    }

    private fun geFragmentBackStackName(fragment: Fragment) = fragment.javaClass.canonicalName

    protected abstract fun createFragment(): Fragment
}
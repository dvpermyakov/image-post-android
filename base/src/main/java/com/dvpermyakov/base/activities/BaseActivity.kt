package com.dvpermyakov.base.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import com.dvpermyakov.base.R
import com.dvpermyakov.base.ioc.IEnrichableItem
import com.dvpermyakov.base.ioc.IInjectorHolder
import io.michaelrocks.lightsaber.Injector
import kotlinx.android.synthetic.main.activity_base.*

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseActivity : AppCompatActivity(), IEnrichableItem {
    private var injector: Injector? = null

    protected open val layoutId = R.layout.activity_base

    var isKeyboardVisible = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (savedInstanceState == null) {
            replaceFragment(createFragment(), false)
        }

        fragmentContainerView.viewTreeObserver.addOnGlobalLayoutListener {
            val dy = fragmentContainerView.rootView.height - fragmentContainerView.height
            val systemBarHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SYSTEM_BAR_DP, resources.displayMetrics)
            isKeyboardVisible = dy > systemBarHeight
        }
    }

    override fun bindInjector(holder: IInjectorHolder) {
        injector = holder.getInjector()
    }

    override fun unbindInjector() {
        injector = null
    }

    fun getContainerViewTreeObserver() = fragmentContainerView.viewTreeObserver

    fun replaceFragment(fragment: Fragment, withBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            if (withBackStack) {
                addToBackStack(geFragmentBackStackName(fragment))
            }
        }.commit()
    }

    fun addFragment(fragment: Fragment, tag: String = "", transit: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
        val transaction = supportFragmentManager.beginTransaction()
        if (tag.isNotEmpty()) {
            transaction.add(R.id.fragmentContainerView, fragment, tag)
        } else {
            transaction.add(R.id.fragmentContainerView, fragment)
        }
        transaction
                .setTransition(transit)
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

    companion object {
        private const val SYSTEM_BAR_DP = 90f
    }
}
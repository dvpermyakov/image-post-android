package com.dvpermyakov.base.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.dvpermyakov.base.R

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected open val layoutId = R.layout.activity_base

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (savedInstanceState == null) {
            replaceFragment(createFragment(), false)
        }
    }

    fun replaceFragment(fragment: Fragment, withBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            if (withBackStack) {
                addToBackStack(geFragmentBackStackName(fragment))
            }
        }.commit()
    }

    fun addFragment(fragment: Fragment, tag: String = "", transit: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
        val transaction = supportFragmentManager.beginTransaction()
        if (tag.isNotEmpty()) {
            transaction.add(R.id.fragment_container, fragment, tag)
        } else {
            transaction.add(R.id.fragment_container, fragment)
        }
        transaction
                .setTransition(transit)
                .addToBackStack(geFragmentBackStackName(fragment))
                .commit()
    }

    private fun geFragmentBackStackName(fragment: Fragment) = fragment.javaClass.canonicalName

    protected abstract fun createFragment(): Fragment
}
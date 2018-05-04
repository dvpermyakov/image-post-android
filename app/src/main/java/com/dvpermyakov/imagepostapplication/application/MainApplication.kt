package com.dvpermyakov.imagepostapplication.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.dvpermyakov.base.ioc.IEnrichableItem
import com.dvpermyakov.base.ioc.IInjectorHolder
import com.dvpermyakov.imagepostapplication.BuildConfig
import com.dvpermyakov.imagepostapplication.di.components.ApplicationComponent
import io.michaelrocks.lightsaber.Lightsaber
import timber.log.Timber

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class MainApplication : Application(), IInjectorHolder {
    private val appInjector by lazy { Lightsaber.get().createInjector(ApplicationComponent(applicationContext)) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        registerActivityLifecycleCallbacks(ActivityEnrichingImplementation(this))
    }

    override fun getInjector() = appInjector
}

interface ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) = Unit
    override fun onActivityStarted(activity: Activity?) = Unit
    override fun onActivityResumed(activity: Activity?) = Unit
    override fun onActivityPaused(activity: Activity?) = Unit
    override fun onActivityStopped(activity: Activity?) = Unit
    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) = Unit
    override fun onActivityDestroyed(activity: Activity?) = Unit
}

class ActivityEnrichingImplementation(private val injectorHolder: IInjectorHolder) : ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        (activity as? IEnrichableItem)?.bindInjector(injectorHolder)
        (activity as? FragmentActivity)?.let { fragmentActivity ->
            fragmentActivity.supportFragmentManager?.registerFragmentLifecycleCallbacks(FragmentEnrichingImplementation(injectorHolder), true)
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        (activity as? IEnrichableItem)?.unbindInjector()
    }
}

class FragmentEnrichingImplementation(private val injectorHolder: IInjectorHolder) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentAttached(fm: FragmentManager?, fragment: Fragment?, context: Context?) {
        (fragment as? IEnrichableItem)?.bindInjector(injectorHolder)
    }

    override fun onFragmentDetached(fm: FragmentManager?, fragment: Fragment?) {
        (fragment as? IEnrichableItem)?.unbindInjector()
    }
}
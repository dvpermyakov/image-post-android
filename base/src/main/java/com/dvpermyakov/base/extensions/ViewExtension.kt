package com.dvpermyakov.base.extensions

import android.view.View
import com.dvpermyakov.base.ioc.IInjectorHolder

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun View?.setVisible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.getInjector() = (context.applicationContext as? IInjectorHolder)?.getInjector()
package com.dvpermyakov.base.extensions

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.dvpermyakov.base.ioc.IInjectorHolder

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun View?.setVisible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.getViewGroupParent() = parent as? ViewGroup

fun View.getCenterX() = x + width / 2

fun View.setCenterX(cx: Float) {
    x = cx - width / 2
}

fun View.getCenterY() = y + height / 2

fun View.setCenterY(cx: Float) {
    y = cx - height / 2
}

fun View.getLocationRect(): Rect {
    val locationPoint = IntArray(2)
    getLocationOnScreen(locationPoint)
    return Rect(locationPoint[0], locationPoint[1], locationPoint[0] + width, locationPoint[1] + height)
}

fun View.isIntersectedByOtherView(other: View): Boolean {
    return Rect.intersects(getLocationRect(), other.getLocationRect())
}

fun View.getInjector() = (context.applicationContext as? IInjectorHolder)?.getInjector()
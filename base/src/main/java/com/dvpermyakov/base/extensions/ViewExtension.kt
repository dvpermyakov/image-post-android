package com.dvpermyakov.base.extensions

import android.graphics.Point
import android.graphics.Rect
import android.view.View
import com.dvpermyakov.base.ioc.IInjectorHolder

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun View?.setVisible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.getLocationPoint(): Point {
    val locationPoint = IntArray(2)
    getLocationOnScreen(locationPoint)
    return Point(locationPoint[0], locationPoint[1])
}

fun View.getViewRect() = Rect(0, 0, width, height)

fun View.getLocationRect(): Rect {
    val point = getLocationPoint()
    return Rect(point.x, point.y, point.x + width, point.y + height)
}

infix fun View.inside(other: View): Boolean {
    return other.getLocationRect().contains(getLocationRect())
}

fun View.getInjector() = (context.applicationContext as? IInjectorHolder)?.getInjector()
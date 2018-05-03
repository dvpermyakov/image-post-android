package com.dvpermyakov.base.extensions

import android.graphics.Matrix
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
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

fun View.getLocationRect(): Rect {
    val point = getLocationPoint()
    return Rect(point.x, point.y, point.x + width, point.y + height)
}

fun View.getInjector() = (context.applicationContext as? IInjectorHolder)?.getInjector()

fun ImageView.getTransitionXFromImageMatrix(): Float {
    val values = FloatArray(9)
    imageMatrix.getValues(values)
    return values[Matrix.MTRANS_X]
}

fun ImageView.getTransitionYFromImageMatrix(): Float {
    val values = FloatArray(9)
    imageMatrix.getValues(values)
    return values[Matrix.MTRANS_Y]
}

fun ImageView.getScaleXFromImageMatrix(): Float {
    val values = FloatArray(9)
    imageMatrix.getValues(values)
    return values[Matrix.MSCALE_X]
}

fun ImageView.getScaleYFromImageMatrix(): Float {
    val values = FloatArray(9)
    imageMatrix.getValues(values)
    return values[Matrix.MSCALE_Y]
}

fun ImageView.getRectFromImageMatrix(imageWidth: Int, imageHeight: Int): Rect {
    val x = getTransitionXFromImageMatrix()
    val y = getTransitionYFromImageMatrix()
    val width = getScaleXFromImageMatrix() * imageWidth
    val height = getScaleYFromImageMatrix() * imageHeight
    return Rect(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt())
}
package com.dvpermyakov.imagepostapplication.models

import android.graphics.Point
import android.graphics.Rect

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

abstract class DraggableModel(val width: Int, val height: Int) {
    var translationX = .25f       // [0f, 1f]
    var translationY = .25f       // [0f, 1f]
    var scaleFactor = 1f          // [0f, 1f]
    var rotationDegrees = 0f
}

fun DraggableModel.centerPoint(viewWidth: Int, viewHeight: Int): Point {
    val x = translationX * viewWidth
    val y = translationY * viewHeight
    return Point(x.toInt(), y.toInt())
}

fun DraggableModel.getRect(viewWidth: Int, viewHeight: Int): Rect {
    val elementHalfWidth = width / 2f * scaleFactor
    val elementHalfHeight = height / 2f * scaleFactor
    val centerPoint = centerPoint(viewWidth, viewHeight)
    return Rect(
            (centerPoint.x - elementHalfWidth).toInt(),
            (centerPoint.y - elementHalfHeight).toInt(),
            (centerPoint.x + elementHalfWidth).toInt(),
            (centerPoint.y + elementHalfHeight).toInt())
}
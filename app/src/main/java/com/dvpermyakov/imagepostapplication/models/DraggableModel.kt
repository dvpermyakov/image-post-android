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

    var state = STATE_IDLE

    companion object {
        const val STATE_IDLE = 0
        const val STATE_MOVE = 1
        const val STATE_SCALE = 2
        const val STATE_ROTATE = 3

        val removeEliminatedStates = listOf(DraggableModel.STATE_SCALE, DraggableModel.STATE_ROTATE)
    }
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
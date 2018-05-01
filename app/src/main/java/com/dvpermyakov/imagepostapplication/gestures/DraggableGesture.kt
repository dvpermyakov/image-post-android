package com.dvpermyakov.imagepostapplication.gestures

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableGesture(context: Context) {
    private var scaleGestureDetector = ScaleGestureDetector(context, ScaleGestureImplementation())

    private var firstPointer: PointerData? = null
    private var onScale = false

    private var scale = 1f

    var listener: Draggable? = null

    fun consumeMotionEvent(event: MotionEvent, x: Float, y: Float, scale: Float): Boolean {
        this.scale = scale

        scaleGestureDetector.onTouchEvent(event)

        if (!onScale) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    firstPointer = PointerData(event.getPointerId(event.actionIndex), x, y, event.rawX, event.rawY)
                }
                MotionEvent.ACTION_MOVE -> {
                    firstPointer?.let { pointerData ->
                        val moveToX = pointerData.downViewX + (event.rawX - pointerData.downEventX)
                        val moveToY = pointerData.downViewY + (event.rawY - pointerData.downEventY)
                        listener?.moveTo(moveToX, moveToY)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    firstPointer = null
                }
                MotionEvent.ACTION_CANCEL -> {
                    firstPointer = null
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    if (event.getPointerId(event.actionIndex) == firstPointer?.id) {
                        firstPointer = null
                    }
                }
            }
        }

        return true
    }

    data class PointerData(var id: Int, var downViewX: Float, var downViewY: Float, var downEventX: Float, var downEventY: Float)

    inner class ScaleGestureImplementation : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            onScale = true
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            onScale = false
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale *= detector.scaleFactor
            listener?.scaleTo(scale)
            return true
        }
    }

    interface Draggable {
        fun moveTo(x: Float, y: Float)
        fun scaleTo(scale: Float)
    }
}
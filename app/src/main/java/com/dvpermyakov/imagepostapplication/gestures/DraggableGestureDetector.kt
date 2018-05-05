package com.dvpermyakov.imagepostapplication.gestures

import android.content.Context
import android.graphics.Matrix
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.almeros.android.multitouch.MoveGestureDetector
import com.almeros.android.multitouch.RotateGestureDetector
import com.dvpermyakov.imagepostapplication.models.DraggableModel
import kotlin.math.max
import kotlin.math.min

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableGestureDetector(
        context: Context,
        private val viewWidth: Int,
        private val viewHeight: Int,
        private val draggable: DraggableModel) {

    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleGestureImplementation())
    private val rotateGestureDetector = UniversalRotateGestureDetector(context, RotateImplementation())
    private val moveGestureDetector = MoveGestureDetector(context, MoveImplementation())

    private val matrix = Matrix()
    private var focusX = draggable.translationX * viewWidth - draggable.width / 2
        set(value) {
            field = value
            draggable.translationX = (value + draggable.width / 2) / viewWidth
        }
    private var focusY = draggable.translationY * viewHeight - draggable.height / 2
        set(value) {
            field = value
            draggable.translationY = (value + draggable.height / 2) / viewHeight
        }

    var listener: Draggable? = null

    fun consumeMotionEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        rotateGestureDetector.onTouchEvent(event)
        moveGestureDetector.onTouchEvent(event)

        listener?.onMatrixChange(getMatrix())

        return true
    }

    fun getMatrix(): Matrix {
        val cx = draggable.scaleFactor * draggable.width / 2
        val cy = draggable.scaleFactor * draggable.height / 2

        with(matrix) {
            reset()
            postScale(draggable.scaleFactor, draggable.scaleFactor)
            postRotate(draggable.rotationDegrees, cx, cy)
            postTranslate(focusX - cx + draggable.width / 2, focusY - cy + draggable.height / 2)
        }

        return matrix
    }

    inner class MoveImplementation : MoveGestureDetector.SimpleOnMoveGestureListener() {
        override fun onMoveBegin(detector: MoveGestureDetector): Boolean {
            listener?.onMoveBegin()
            return true
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            val delta = detector.focusDelta
            focusX += delta.x
            focusY += delta.y
            listener?.onMove()
            return true
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {
            listener?.onMoveEnd()
        }
    }

    inner class ScaleGestureImplementation : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            draggable.scaleFactor = max(SCALE_FACTOR_MIN, min(draggable.scaleFactor * detector.scaleFactor, SCALE_FACTOR_MAX))
            return true
        }
    }

    inner class RotateImplementation : RotateGestureDetector.SimpleOnRotateGestureListener() {
        override fun onRotate(detector: RotateGestureDetector): Boolean {
            draggable.rotationDegrees -= detector.rotationDegreesDelta
            return true
        }
    }

    interface Draggable {
        fun onMoveBegin()
        fun onMove()
        fun onMoveEnd()
        fun onMatrixChange(matrix: Matrix)
    }

    companion object {
        private const val SCALE_FACTOR_MIN = .5f
        private const val SCALE_FACTOR_MAX = 2f
    }
}
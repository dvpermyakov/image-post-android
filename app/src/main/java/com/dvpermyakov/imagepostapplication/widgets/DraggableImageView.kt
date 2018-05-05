package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.dvpermyakov.base.extensions.getLocationPoint
import com.dvpermyakov.base.extensions.getLocationRect
import com.dvpermyakov.base.extensions.getPointersCenter
import com.dvpermyakov.imagepostapplication.gestures.DraggableGestureDetector
import com.dvpermyakov.imagepostapplication.models.DraggableModel
import com.dvpermyakov.imagepostapplication.models.getRect

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : ImageView, IDisposableView {
    private val draggableGestureDetector by lazy {
        DraggableGestureDetector(context, width, height, draggableModel).apply {
            listener = object : DraggableGestureDetector.Draggable {
                override fun onMoveBegin() {
                    motionStateListener?.invoke(true, isInsideParent)
                }

                override fun onMove() {
                    positionChangeListener?.invoke(isInsideParent)
                }

                override fun onMoveEnd() {
                    motionStateListener?.invoke(false, isInsideParent)
                }

                override fun onMatrixChange(matrix: Matrix) {
                    imageMatrix = matrix
                }
            }
        }
    }

    private var isDragged = false
    private var isInsideParent = true

    var positionChangeListener: ((isInsideParent: Boolean) -> Unit)? = null
    var motionStateListener: ((isInMotion: Boolean, isInsideParent: Boolean) -> Unit)? = null

    lateinit var draggableModel: DraggableModel

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        imageMatrix = draggableGestureDetector.getMatrix()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var consumed = false

        val centerPoint = event.getPointersCenter()
        val eventX = centerPoint.x
        val eventY = centerPoint.y

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (event.pointerCount == 1 || isDragged) {
                    if (draggableModel.getRect(width, height).contains(eventX, eventY)) {
                        isDragged = true
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                isDragged = false
                draggableGestureDetector.consumeMotionEvent(event)
            }
            MotionEvent.ACTION_CANCEL -> {
                isDragged = false
                draggableGestureDetector.consumeMotionEvent(event)
            }
        }

        if (isDragged) {
            consumed = draggableGestureDetector.consumeMotionEvent(event)
            checkBoundaries(eventX, eventY)
        }

        return consumed
    }

    override fun onDispose() {
        positionChangeListener = null
        motionStateListener = null
    }

    fun isIntersectedBy(other: View): Boolean {
        val imageRect = draggableModel.getRect(width, height)
        val locationPoint = getLocationPoint()
        val locationRect = Rect(
                locationPoint.x + imageRect.left,
                locationPoint.y + imageRect.top,
                locationPoint.x + imageRect.right,
                locationPoint.y + imageRect.bottom)
        return Rect.intersects(locationRect, other.getLocationRect())
    }

    private fun checkBoundaries(eventX: Int, eventY: Int) {
        isInsideParent = Rect(0, 0, width, height).contains(eventX, eventY)
    }
}
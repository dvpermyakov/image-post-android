package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.dvpermyakov.base.extensions.*
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.gestures.DraggableGestureDetector
import com.dvpermyakov.imagepostapplication.models.DraggableModel
import com.dvpermyakov.imagepostapplication.models.getRect

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : AppCompatImageView, IDisposableView {
    private val imageIntersectOffset = resources.getDimensionPixelOffset(R.dimen.size_medium)
    private val boundaryOffset = resources.getDimensionPixelOffset(R.dimen.size_xlarge)
    private val draggableGestureDetector by lazy {
        DraggableGestureDetector(context, width, height, draggableModel).apply {
            listener = object : DraggableGestureDetector.Draggable {
                override fun onStateChange(isInMotion: Boolean) {
                    if (isRemovable) {
                        isRemovable = draggableModel.state !in DraggableModel.removeEliminatedStates
                    }
                    draggableStateListener?.invoke(isInMotion, isInsideParent, isRemovable)
                }

                override fun onPositionChange() {
                    positionChangeListener?.invoke(isInsideParent, isRemovable)
                }

                override fun onMatrixChange(matrix: Matrix) {
                    imageMatrix = matrix
                }
            }
        }
    }

    private var isRemovable = false
    private var isDragged = false
    private var isInsideParent = true

    var positionChangeListener: ((isInsideParent: Boolean, isRemovable: Boolean) -> Unit)? = null
    var draggableStateListener: ((isInMotion: Boolean, isInsideParent: Boolean, isRemovable: Boolean) -> Unit)? = null

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
                        isRemovable = true
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
            if (event.pointerCount == 1 && draggableModel.getRect(width, height).contains(eventX, eventY)) {
                checkBoundaries(eventX, eventY)
            } else {
                isInsideParent = true
            }
        }

        return consumed
    }

    override fun onDispose() {
        positionChangeListener = null
        draggableStateListener = null
    }

    fun isIntersectedBy(other: View): Boolean {
        val imageRect = draggableModel.getRect(width, height).setOffset(-imageIntersectOffset)
        val locationPoint = getLocationPoint()
        val locationRect = Rect(
                locationPoint.x + imageRect.left,
                locationPoint.y + imageRect.top,
                locationPoint.x + imageRect.right,
                locationPoint.y + imageRect.bottom)
        return Rect.intersects(locationRect, other.getLocationRect())
    }

    private fun checkBoundaries(eventX: Int, eventY: Int) {
        isInsideParent = getViewRect().setOffset(boundaryOffset).contains(eventX, eventY)
    }
}
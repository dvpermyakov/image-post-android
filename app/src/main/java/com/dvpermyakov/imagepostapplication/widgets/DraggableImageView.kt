package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import com.dvpermyakov.imagepostapplication.gestures.DraggableGesture
import com.dvpermyakov.imagepostapplication.models.DraggableModel

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : ImageView, IDisposableView {
    private val draggableGesture = DraggableGesture(context).apply {
        listener = object : DraggableGesture.Draggable {
            override fun startMove() {
                motionStateListener?.invoke(true, isInsideParent)
            }

            override fun moveTo(x: Float, y: Float) {
                this@DraggableImageView.x = x
                this@DraggableImageView.y = y
            }

            override fun endMove() {
                motionStateListener?.invoke(false, isInsideParent)
            }

            override fun scaleTo(scale: Float) {
                scaleX = scale
                scaleY = scale
            }
        }
    }
    private var isInsideParent = true
        set(value) {
            if (field != value) {
                field = value
                boundaryStateListener?.invoke(value)
            }
        }

    var motionStateListener: ((isInMotion: Boolean, isInsideParent: Boolean) -> Unit)? = null
    var boundaryStateListener: ((isInsideParent: Boolean) -> Unit)? = null

    var draggableModel: DraggableModel? = null
        set(value) {
            field = value
            x = value?.x ?: 0f
            y = value?.y ?: 0f
            scaleX = value?.scale ?: 1f
            scaleY = value?.scale ?: 1f
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setX(x: Float) {
        super.setX(x)
        invalidateDraggableModel()
    }

    override fun setY(y: Float) {
        super.setY(y)
        invalidateDraggableModel()
    }

    override fun setScaleX(scaleX: Float) {
        super.setScaleX(scaleX)
        invalidateDraggableModel()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        checkBoundaries()
        return draggableGesture.consumeMotionEvent(event, x, y, scaleX)
    }

    override fun onDispose() {
        motionStateListener = null
        boundaryStateListener = null
        draggableModel = null
    }

    private fun invalidateDraggableModel() {
        draggableModel?.x = x
        draggableModel?.y = y
        draggableModel?.scale = scaleX
    }

    private fun checkBoundaries() {
        val parent = parent as ViewGroup
        val rect = Rect()
        getHitRect(rect)
        isInsideParent = rect.left >= 0 && rect.top >= 0 && rect.right <= parent.width && rect.bottom <= parent.height
    }
}
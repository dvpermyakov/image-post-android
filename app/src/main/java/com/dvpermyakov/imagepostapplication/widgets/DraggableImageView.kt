package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.dvpermyakov.base.extensions.*
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
                setCenterX(x)
                setCenterY(y)
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
            if (field != value) {
                field = value
                applyDraggableModel()
            }
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setX(x: Float) {
        super.setX(x)
        getViewGroupParent()?.let { parent ->
            draggableModel?.translationX = getCenterX() / parent.width
        }
    }

    override fun setY(y: Float) {
        super.setY(y)
        getViewGroupParent()?.let { parent ->
            draggableModel?.translationY = getCenterY() / parent.height
        }
    }

    override fun setScaleX(scaleX: Float) {
        super.setScaleX(scaleX)
        draggableModel?.scaleX = scaleX
    }

    override fun setScaleY(scaleY: Float) {
        super.setScaleY(scaleY)
        draggableModel?.scaleY = scaleY
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        applyDraggableModel()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        checkBoundaries(event.x.toInt(), event.y.toInt())
        return draggableGesture.consumeMotionEvent(event, getCenterX(), getCenterY(), scaleX)
    }

    override fun onDispose() {
        motionStateListener = null
        boundaryStateListener = null
        draggableModel = null
    }

    private fun applyDraggableModel() {
        getViewGroupParent()?.let { parent ->
            draggableModel?.let { model ->
                setCenterX(model.translationX * parent.width)
                setCenterY(model.translationY * parent.height)
                scaleX = model.scaleX
                scaleY = model.scaleY
            }
        }
    }

    private fun checkBoundaries(eventX: Int, eventY: Int) {
        getViewGroupParent()?.let { parent ->
            val rect = Rect()
            getHitRect(rect)
            isInsideParent =
                    rect.left + eventX >= 0 &&
                    rect.top + eventY >= 0 &&
                    rect.right - (width - eventX) <= parent.width &&
                    rect.bottom - (height - eventY) <= parent.height
        }
    }
}
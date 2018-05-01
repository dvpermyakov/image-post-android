package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.dvpermyakov.imagepostapplication.gestures.DraggableGesture
import com.dvpermyakov.imagepostapplication.models.DraggableModel

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : ImageView {
    private val draggableGesture = DraggableGesture(context).apply {
        listener = object : DraggableGesture.Draggable {
            override fun moveTo(x: Float, y: Float) {
                this@DraggableImageView.x = x
                this@DraggableImageView.y = y
            }

            override fun scaleTo(scale: Float) {
                scaleX = scale
                scaleY = scale
            }
        }
    }

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
        return draggableGesture.consumeMotionEvent(event, x, y, scaleX)
    }

    private fun invalidateDraggableModel() {
        draggableModel?.x = x
        draggableModel?.y = y
        draggableModel?.scale = scaleX
    }
}
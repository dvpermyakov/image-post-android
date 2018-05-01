package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : ImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var downViewX = 0f
    private var downViewY = 0f

    private var downEventX = 0f
    private var downEventY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.pointerCount == 1) {
                    downViewX = x
                    downViewY = y
                    downEventX = event.rawX
                    downEventY = event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    x = downViewX + event.rawX - downEventX
                    y = downViewY + event.rawY - downEventY
                }
            }
        }
        return true
    }
}
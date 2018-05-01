package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

class DraggableImageView : ImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var downMarginLeft = 0
    private var downMarginTop = 0

    private var downX = 0f
    private var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.pointerCount == 1) {
                    (layoutParams as ViewGroup.MarginLayoutParams).let { layoutParams ->
                        downMarginLeft = layoutParams.leftMargin
                        downMarginTop = layoutParams.topMargin
                    }
                    downX = event.rawX
                    downY = event.rawY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    val layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
                        leftMargin = downMarginLeft + (event.rawX - downX).toInt()
                        topMargin = downMarginTop + (event.rawY - downY).toInt()
                    }
                    setLayoutParams(layoutParams)
                }
            }
        }
        return true
    }
}
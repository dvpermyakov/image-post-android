package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.widget.EditText
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.utils.PaintFactory

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

class ColoredEditTextView : EditText {
    private val radius = resources.getDimension(R.dimen.size_xsmall)
    private val offset = resources.getDimension(R.dimen.size_small)

    private var paint: Paint? = null
    private var rectList = mutableListOf<RectF>()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidateRectList()
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        invalidateRectList()
    }

    override fun onDraw(canvas: Canvas) {
        if (paint != null && text.isNotEmpty()) {
            rectList.forEach { rect ->
                canvas.drawRoundRect(rect, radius, radius, paint)
            }
        }
        super.onDraw(canvas)
    }

    fun setTextBackgroundColor(@ColorInt color: Int) {
        paint = PaintFactory.createColorPaint(color)
        invalidate()
    }

    private fun invalidateRectList() {
        layout?.let { layout ->
            rectList.clear()
            for (index in 0 until layout.lineCount) {
                if (layout.getLineRight(index) - layout.getLineLeft(index) > EPSILON) {
                    rectList.add(getRectFromLayout(index))
                }
            }
        }
    }

    private fun getRectFromLayout(index: Int): RectF {
        return RectF(
                layout.getLineLeft(index) - offset + paddingLeft,
                layout.getLineTop(index).toFloat() - offset + paddingTop,
                layout.getLineRight(index) + offset + paddingLeft,
                layout.getLineBottom(index).toFloat() + offset + paddingTop)
    }

    companion object {
        private const val EPSILON = 10
    }
}
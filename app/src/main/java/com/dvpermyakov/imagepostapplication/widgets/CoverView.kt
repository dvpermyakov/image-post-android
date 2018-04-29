package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.dvpermyakov.imagepostapplication.models.ColorCoverModel
import com.dvpermyakov.imagepostapplication.models.CoverModel

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

class CoverView : View {
    private var paint: Paint? = null
    private var rect: RectF? = null

    var cover: CoverModel? = null
        set(value) {
            if (field != value) {
                field = value
                invalidatePaint()
                invalidate()
            }
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        invalidatePaint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect?.let { rect ->
            paint?.let { paint ->
                canvas.drawRect(rect, paint)
            }
        }
    }

    private fun invalidatePaint() {
        rect?.let { rect ->
            cover?.let { cover ->
                when (cover) {
                    is ColorCoverModel -> {
                        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            shader = LinearGradient(0f, 0f, rect.right, rect.bottom, cover.colorStart, cover.colorEnd, Shader.TileMode.MIRROR)
                        }
                    }
                }
            }
        }
    }
}
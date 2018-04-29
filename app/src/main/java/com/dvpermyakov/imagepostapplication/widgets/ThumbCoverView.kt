package com.dvpermyakov.imagepostapplication.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.dvpermyakov.base.extensions.getCompatColor
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.models.ColorCoverModel
import com.dvpermyakov.imagepostapplication.models.EmptyColorCoverModel
import com.dvpermyakov.imagepostapplication.models.ImageCoverModel
import com.dvpermyakov.imagepostapplication.models.SelectableCoverModel
import com.dvpermyakov.imagepostapplication.utils.PaintUtils

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

class ThumbCoverView : View {
    private val radius by lazy { resources.getDimension(R.dimen.size_xsmall) }
    private val strokePaint by lazy { PaintUtils.getStrokePaint(context.getCompatColor(R.color.colorPrimary), STROKE_WIDTH) }
    private var paint: Paint? = null
    private var rect: RectF? = null

    var selectableCover: SelectableCoverModel? = null
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
            selectableCover?.let { selectableCover ->
                paint?.let { paint ->
                    if (selectableCover.selected) {
                        canvas.scale(SELECTED_SCALE_STROKE, SELECTED_SCALE_STROKE, rect.centerX(), rect.centerY())
                        canvas.drawRoundRect(rect, radius, radius, strokePaint)
                        canvas.scale(SELECTED_SCALE_FILL, SELECTED_SCALE_FILL, rect.centerX(), rect.centerY())
                        canvas.drawRect(rect, paint)
                    } else {
                        canvas.drawRoundRect(rect, radius, radius, paint)
                    }
                }
            }
        }
    }

    private fun invalidatePaint() {
        rect?.let { rect ->
            selectableCover?.cover?.let { cover ->
                paint = when (cover) {
                    is EmptyColorCoverModel -> PaintUtils.getGradientColorPaint(cover.colorStart, cover.colorEnd, rect)
                    is ColorCoverModel -> PaintUtils.getGradientColorPaint(cover.colorStart, cover.colorEnd, rect)
                    is ImageCoverModel -> PaintUtils.getImagePaint(resources, cover.image)
                    else -> PaintUtils.getEmptyPaint()
                }
            }
        }
    }

    companion object {
        private const val SELECTED_SCALE_STROKE = .92f
        private const val SELECTED_SCALE_FILL = .8f

        private const val STROKE_WIDTH = 10f
    }
}
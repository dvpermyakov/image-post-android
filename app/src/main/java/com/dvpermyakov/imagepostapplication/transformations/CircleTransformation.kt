package com.dvpermyakov.imagepostapplication.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.support.annotation.ColorInt
import androidx.core.graphics.withScale
import com.dvpermyakov.imagepostapplication.utils.PaintUtils
import com.squareup.picasso.Transformation

/**
 * Created by dmitrypermyakov on 02/05/2018.
 */

class CircleTransformation(@ColorInt strokeColor: Int, @ColorInt fillColor: Int) : Transformation {
    private val strokePaint = PaintUtils.getStrokePaint(strokeColor, STROKE_WIDTH)
    private var fillPaint = PaintUtils.getColorPaint(fillColor)

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.max(source.width, source.height)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val rect = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        val canvas = Canvas(bitmap)

        drawCircleBackground(canvas, rect)
        drawSource(canvas, source, rect)

        source.recycle()

        return bitmap
    }

    override fun key() = KEY_TRANSFORMATION

    private fun drawSource(canvas: Canvas, source: Bitmap, rect: RectF) {
        val paint = PaintUtils.getBitmapPaint(source)
        canvas.withScale(SCALE_SOURCE, SCALE_SOURCE, rect.centerX(), rect.centerY(), {
            drawRect(rect, paint)
        })
    }

    private fun drawCircleBackground(canvas: Canvas, rect: RectF) {
        val radius = Math.min(rect.centerX(), rect.centerY())
        canvas.withScale(SCALE_CIRCLE, SCALE_CIRCLE, rect.centerX(), rect.centerY(), {
            drawCircle(rect.centerX(), rect.centerY(), radius, strokePaint)
            drawCircle(rect.centerX(), rect.centerY(), radius, fillPaint)
        })
    }

    companion object {
        private const val SCALE_CIRCLE = .9f
        private const val SCALE_SOURCE = .6f
        private const val STROKE_WIDTH = 30f
        private const val KEY_TRANSFORMATION = "CircleTransformation"
    }
}
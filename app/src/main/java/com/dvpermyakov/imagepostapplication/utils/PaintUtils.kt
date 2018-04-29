package com.dvpermyakov.imagepostapplication.utils

import android.content.res.Resources
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

object PaintUtils {
    fun getGradientColorPaint(@ColorInt colorStart: Int, @ColorInt colorEnd: Int, rect: RectF) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(0f, 0f, rect.right, rect.bottom, colorStart, colorEnd, Shader.TileMode.MIRROR)
    }

    fun getImagePaint(resources: Resources, @DrawableRes image: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val bitmap = BitmapFactory.decodeResource(resources, image)
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    fun getEmptyPaint() = Paint().apply { alpha = 0 }

    fun getStrokePaint(@ColorInt color: Int, strokeWidth: Float) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color
        this.strokeWidth = strokeWidth
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }
}
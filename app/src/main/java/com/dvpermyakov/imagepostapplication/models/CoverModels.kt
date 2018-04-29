package com.dvpermyakov.imagepostapplication.models

import android.content.Context
import android.support.annotation.ColorInt
import com.dvpermyakov.base.extensions.getCompatColor
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class CoverModel {
    companion object {
        fun getDefaults(ctx: Context) = listOf(
                ColorCoverModel.getModelEmpty(ctx),
                ColorCoverModel.getModelBlue(ctx),
                ColorCoverModel.getModelGreen(ctx),
                ColorCoverModel.getModelOrange(ctx),
                ColorCoverModel.getModelRed(ctx),
                ColorCoverModel.getModelPurple(ctx))
    }
}

data class ColorCoverModel(@ColorInt val colorStart: Int, @ColorInt val colorEnd: Int) : CoverModel() {
    companion object {
        fun getModelEmpty(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerEmpty),
                ctx.getCompatColor(R.color.colorPickerEmpty))

        fun getModelBlue(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerBlueStart),
                ctx.getCompatColor(R.color.colorPickerBlueEnd))

        fun getModelGreen(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerGreenStart),
                ctx.getCompatColor(R.color.colorPickerGreenEnd))

        fun getModelOrange(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerOrangeStart),
                ctx.getCompatColor(R.color.colorPickerOrangeEnd))

        fun getModelRed(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerRedStart),
                ctx.getCompatColor(R.color.colorPickerRedEnd))

        fun getModelPurple(ctx: Context) = ColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerPurpleStart),
                ctx.getCompatColor(R.color.colorPickerPurpleEnd))
    }
}
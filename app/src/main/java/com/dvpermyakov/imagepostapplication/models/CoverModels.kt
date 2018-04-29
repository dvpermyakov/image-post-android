package com.dvpermyakov.imagepostapplication.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import com.dvpermyakov.base.extensions.getCompatColor
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class CoverModel : Parcelable {
    companion object {
        fun getDefaults(ctx: Context) = listOf(
                ColorCoverModel.getModelEmpty(ctx),
                ColorCoverModel.getModelBlue(ctx),
                ColorCoverModel.getModelGreen(ctx),
                ColorCoverModel.getModelOrange(ctx),
                ColorCoverModel.getModelRed(ctx),
                ColorCoverModel.getModelPurple(ctx),
                ImageCoverModel.getModelBeach(),
                ImageCoverModel.getModelStars())
    }
}

data class ColorCoverModel(@ColorInt val colorStart: Int, @ColorInt val colorEnd: Int) : CoverModel() {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(colorStart)
        parcel.writeInt(colorEnd)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ColorCoverModel> {
            override fun createFromParcel(parcel: Parcel) = ColorCoverModel(parcel)
            override fun newArray(size: Int) = arrayOfNulls<ColorCoverModel?>(size)
        }

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

data class ImageCoverModel(@DrawableRes val image: Int) : CoverModel() {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ImageCoverModel> {
            override fun createFromParcel(parcel: Parcel) = ImageCoverModel(parcel)
            override fun newArray(size: Int) = arrayOfNulls<ImageCoverModel?>(size)
        }

        fun getModelBeach() = ImageCoverModel(R.drawable.thumb_beach)
        fun getModelStars() = ImageCoverModel(R.drawable.thumb_stars)
    }
}
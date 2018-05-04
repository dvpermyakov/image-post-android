package com.dvpermyakov.imagepostapplication.models

import android.content.Context
import android.os.Parcel
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import com.dvpermyakov.base.extensions.getCompatColor
import com.dvpermyakov.base.models.KParcelable
import com.dvpermyakov.base.models.parcelableCreator
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class CoverModel : KParcelable {
    companion object {
        fun getDefaults(ctx: Context) = listOf(
                EmptyColorCoverModel.getModelEmpty(ctx),
                ColorCoverModel.getModelBlue(ctx),
                ColorCoverModel.getModelGreen(ctx),
                ColorCoverModel.getModelOrange(ctx),
                ColorCoverModel.getModelRed(ctx),
                ColorCoverModel.getModelPurple(ctx),
                ImageCoverModel.getModelBeach(),
                ImageCoverModel.getModelStars())
    }
}

data class EmptyColorCoverModel(@ColorInt val colorStart: Int, @ColorInt val colorEnd: Int) : CoverModel() {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(colorStart)
        writeInt(colorEnd)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::EmptyColorCoverModel)

        fun getModelEmpty(ctx: Context) = EmptyColorCoverModel(
                ctx.getCompatColor(R.color.colorPickerEmpty),
                ctx.getCompatColor(R.color.colorPickerEmpty))
    }
}

data class ColorCoverModel(@ColorInt val colorStart: Int, @ColorInt val colorEnd: Int) : CoverModel() {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(colorStart)
        writeInt(colorEnd)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ColorCoverModel)

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

data class ImageCoverModel(@DrawableRes val imageThumb: Int, @DrawableRes val imageLarge: Int) : CoverModel() {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(imageThumb)
        writeInt(imageLarge)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ImageCoverModel)

        fun getModelBeach() = ImageCoverModel(R.drawable.thumb_beach, R.drawable.large_beach)
        fun getModelStars() = ImageCoverModel(R.drawable.thumb_stars, R.drawable.large_stars)
    }
}

data class FileCoverModel(val path: String) : CoverModel() {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(path)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::FileCoverModel)
    }
}
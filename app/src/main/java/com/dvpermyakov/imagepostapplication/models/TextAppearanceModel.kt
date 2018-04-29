package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import android.os.Parcelable
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

data class TextAppearanceModel(var backgroundType: Int = TYPE_BACKGROUND_EMPTY) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(backgroundType)
    }

    fun nextBackground() {
        backgroundType += 1
        backgroundType %= BACKGROUND_TYPE_SIZE
    }

    fun getBackgroundColor() = when (backgroundType) {
        TYPE_BACKGROUND_MAIN -> R.color.colorBackground
        TYPE_BACKGROUND_BLUE -> R.color.colorPrimaryLight
        else -> R.color.colorTransparent
    }

    fun getTextColor(cover: CoverModel?) = when {
        backgroundType == TYPE_BACKGROUND_BLUE -> R.color.textColorWhite
        backgroundType == TYPE_BACKGROUND_MAIN -> R.color.textColorPrimary
        cover is ColorCoverModel || cover is ImageCoverModel -> R.color.textColorWhite
        else -> R.color.textColorPrimary
    }

    companion object {
        const val TYPE_BACKGROUND_EMPTY = 0
        const val TYPE_BACKGROUND_MAIN = 1
        const val TYPE_BACKGROUND_BLUE = 2

        const val BACKGROUND_TYPE_SIZE = 3

        @JvmField
        val CREATOR = object : Parcelable.Creator<TextAppearanceModel> {
            override fun createFromParcel(parcel: Parcel) = TextAppearanceModel(parcel)
            override fun newArray(size: Int) = arrayOfNulls<TextAppearanceModel?>(size)
        }
    }
}
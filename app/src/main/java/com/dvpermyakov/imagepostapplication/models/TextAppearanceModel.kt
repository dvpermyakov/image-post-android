package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import com.dvpermyakov.base.models.KParcelable
import com.dvpermyakov.base.models.parcelableCreator
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

data class TextAppearanceModel(private var backgroundType: Int = TYPE_BACKGROUND_EMPTY) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(backgroundType)
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
        cover is ColorCoverModel || cover is ImageCoverModel || cover is FileCoverModel -> R.color.textColorWhite
        else -> R.color.textColorPrimary
    }

    fun getHintTextColor(cover: CoverModel?) = when (cover) {
        is ColorCoverModel, is ImageCoverModel, is FileCoverModel -> R.color.textColorWhite
        else -> R.color.textColorSecondary
    }

    companion object {
        const val TYPE_BACKGROUND_EMPTY = 0
        const val TYPE_BACKGROUND_MAIN = 1
        const val TYPE_BACKGROUND_BLUE = 2

        const val BACKGROUND_TYPE_SIZE = 3

        @JvmField
        val CREATOR = parcelableCreator(::TextAppearanceModel)
    }
}
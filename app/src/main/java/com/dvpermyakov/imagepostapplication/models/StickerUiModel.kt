package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import com.dvpermyakov.base.models.KParcelable
import com.dvpermyakov.base.models.parcelableCreator

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

data class StickerUiModel(private val size: Int, val sticker: StickerModel) : DraggableModel(size, size), KParcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readParcelable<StickerModel>(StickerModel::class.java.classLoader))

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(size)
        writeParcelable(sticker, flags)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::StickerUiModel)
    }
}
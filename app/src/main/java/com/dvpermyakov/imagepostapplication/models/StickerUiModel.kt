package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

data class StickerUiModel(val sticker: StickerModel) : DraggableModel(), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<StickerModel>(StickerModel::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(sticker, flags)
    }

    companion object CREATOR : Parcelable.Creator<StickerUiModel> {
        override fun createFromParcel(parcel: Parcel) = StickerUiModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<StickerUiModel>(size)
    }
}
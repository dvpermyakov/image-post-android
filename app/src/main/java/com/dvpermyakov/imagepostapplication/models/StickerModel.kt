package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

data class StickerModel(val image: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
    }

    companion object CREATOR : Parcelable.Creator<StickerModel> {
        override fun createFromParcel(parcel: Parcel) = StickerModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<StickerModel>(size)
    }
}
package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

data class SelectableCoverModel(val cover: CoverModel, var selected: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<CoverModel>(CoverModel::class.java.classLoader), parcel.readByte() != 0.toByte())

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (selected) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<SelectableCoverModel> {
        override fun createFromParcel(parcel: Parcel) = SelectableCoverModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<SelectableCoverModel?>(size)
    }

}
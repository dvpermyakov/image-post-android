package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import com.dvpermyakov.base.models.KParcelable
import com.dvpermyakov.base.models.parcelableCreator

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

data class SelectableCoverModel(val cover: CoverModel, var selected: Boolean) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<CoverModel>(CoverModel::class.java.classLoader), parcel.readByte() != 0.toByte())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(cover, flags)
        writeByte(if (selected) 1 else 0)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::SelectableCoverModel)
    }
}
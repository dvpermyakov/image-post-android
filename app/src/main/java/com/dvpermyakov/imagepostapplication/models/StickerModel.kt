package com.dvpermyakov.imagepostapplication.models

import android.os.Parcel
import com.dvpermyakov.base.models.KParcelable
import com.dvpermyakov.base.models.parcelableCreator

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

data class StickerModel(val image: String) : KParcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(image)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::StickerModel)
    }
}
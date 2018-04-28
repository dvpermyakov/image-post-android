package com.dvpermyakov.imagepostapplication.models

import android.graphics.Color
import android.support.annotation.ColorInt

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class CoverModel {
    companion object {
        fun getDefault() = listOf(
                ColorCoverModel(Color.WHITE),
                ColorCoverModel(Color.BLUE),
                ColorCoverModel(Color.GREEN))
    }
}

data class ColorCoverModel(@ColorInt val color: Int) : CoverModel()
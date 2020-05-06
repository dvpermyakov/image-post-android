package com.dvpermyakov.imagepostapplication.interactors

import androidx.annotation.DrawableRes
import com.dvpermyakov.imagepostapplication.repositories.BitmapRepository
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

class BitmapInteractor @Inject constructor(private val bitmapRepository: BitmapRepository) {

    fun getBitmap(path: String, size: Int) = bitmapRepository.getBitmap(path, size)

    fun getBitmap(@DrawableRes drawable: Int) = bitmapRepository.getBitmap(drawable)
}
package com.dvpermyakov.imagepostapplication.interactors

import com.dvpermyakov.imagepostapplication.repositories.StickerRepository
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class StickerInteractor @Inject constructor(private val stickerRepository: StickerRepository) {
    fun getStickers() = stickerRepository.getStickers()
}
package com.dvpermyakov.imagepostapplication.utils

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

object ImagePostApplicationConstants {
    const val INTENT_EXTRA_STICKER_MODEL = "intent_extra_sticker_model"

    private val nextId = AtomicInteger(1)

    fun getNextId() = nextId.incrementAndGet()
}
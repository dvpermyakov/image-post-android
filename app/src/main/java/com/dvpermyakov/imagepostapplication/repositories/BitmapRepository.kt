package com.dvpermyakov.imagepostapplication.repositories

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.annotation.DrawableRes
import androidx.core.util.lruCache
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

@Singleton
class BitmapRepository @Inject constructor(private val resources: Resources) {
    private val bitmapCache = lruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 1024 / 8).toInt())

    fun getBitmap(path: String, size: Int): Single<Bitmap> {
        return bitmapCache.get(path)?.let {
            Single.just(it)
        } ?: Single.create<Bitmap> { decodeFile(path, size, it) }
    }

    fun getBitmap(@DrawableRes drawable: Int) = Single.create<Bitmap> { decodeDrawable(drawable, it) }

    private fun decodeFile(path: String, size: Int, emitter: SingleEmitter<Bitmap>) {
        try {
            val bitmap = BitmapFactory.decodeFile(path)
            val thumb = ThumbnailUtils.extractThumbnail(bitmap, size, size)
            bitmap.recycle()
            bitmapCache.put(path, thumb)
            emitter.onSuccess(thumb)
        } catch (ex: Exception) {
            emitter.onError(ex)
        }
    }

    private fun decodeDrawable(@DrawableRes drawable: Int, emitter: SingleEmitter<Bitmap>) {
        try {
            val bitmap = BitmapFactory.decodeResource(resources, drawable)
            emitter.onSuccess(bitmap)
        } catch (ex: Exception) {
            emitter.onError(ex)
        }
    }
}
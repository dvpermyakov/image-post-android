package com.dvpermyakov.imagepostapplication.interactors

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

class GalleryImageInteractor @Inject constructor(private val contentResolver: ContentResolver) {
    fun getImagePath(uri: Uri) = Single.create<String> { emitter ->
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, filePathColumn, null, null, null)?.use { cursor ->
            try {
                cursor.moveToFirst()
                emitter.onSuccess(cursor.getString(cursor.getColumnIndex(filePathColumn[0])))
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }

    fun saveImage(source: Bitmap, title: String, description: String) = Single.create<String> { emitter ->
        try {
            val url = MediaStore.Images.Media.insertImage(contentResolver, source, title, description)
            emitter.onSuccess(url)
        } catch (ex: Exception) {
            emitter.onError(ex)
        }
    }
}
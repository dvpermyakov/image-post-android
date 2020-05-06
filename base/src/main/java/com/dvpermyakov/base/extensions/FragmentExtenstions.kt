package com.dvpermyakov.base.extensions

import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

fun Fragment.requestPermission(permission: String, requestCode: Int) {
    requestPermissions(arrayOf(permission), requestCode)
}

fun Fragment.startActivityForImageFromGallery(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
    startActivityForResult(intent, requestCode)
}
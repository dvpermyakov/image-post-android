package com.dvpermyakov.base.extensions

import android.content.Intent
import android.support.v4.app.Fragment

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
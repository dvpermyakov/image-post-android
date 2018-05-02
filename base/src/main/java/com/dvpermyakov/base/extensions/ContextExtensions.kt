package com.dvpermyakov.base.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
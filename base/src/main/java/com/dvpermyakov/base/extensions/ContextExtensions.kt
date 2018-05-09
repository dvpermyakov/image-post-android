package com.dvpermyakov.base.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.getInputManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.getVibrationManager() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

fun Context.vibrate(duration: Long) {
    getVibrationManager().let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            it.vibrate(duration)
        }
    }
}
package com.dvpermyakov.base.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes

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

fun Context.toast(@StringRes res: Int) {
    Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
}
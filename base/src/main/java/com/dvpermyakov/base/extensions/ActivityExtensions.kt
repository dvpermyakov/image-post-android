package com.dvpermyakov.base.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import com.dvpermyakov.base.dialogs.ProgressDialogFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun Activity.showKeyboard() {
    currentFocus?.let { focus ->
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(focus, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Activity.hideKeyboard() {
    currentFocus?.windowToken?.let { token ->
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.shouldShowRequestPermissionDetails(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Activity.openApplicationSettings() {
    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}

fun FragmentActivity.showLoadingDialog(@StringRes messageId: Int, tag: String) {
    if ((supportFragmentManager.findFragmentByTag(tag) as? ProgressDialogFragment) == null) {
        ProgressDialogFragment.newInstance(messageId).apply {
            show(supportFragmentManager, tag)
        }
    }
}

fun FragmentActivity.hideLoadingDialog(tag: String) {
    (supportFragmentManager.findFragmentByTag(tag) as? ProgressDialogFragment)?.dismiss()
}
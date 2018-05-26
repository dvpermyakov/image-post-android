package com.dvpermyakov.base.extensions

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri
import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.base.dialogs.ProgressDialogFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun Activity.showKeyboard() {
    currentFocus?.let { focus ->
        getInputManager().showSoftInput(focus, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Activity.hideKeyboard() {
    currentFocus?.windowToken?.let { token ->
        getInputManager().hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.shouldShowRequestPermissionDetails(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun Activity.openApplicationSettings() {
    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, "package:$packageName".toUri()).apply {
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

fun FragmentActivity.hideLoadingDialog(tag: String, attemptCount: Int = 3) {
    (supportFragmentManager.findFragmentByTag(tag) as? ProgressDialogFragment)?.dismiss() ?: run {
        Handler().postDelayed({
            hideLoadingDialog(tag, attemptCount - 1)
        }, 100)
    }
}

inline fun BaseActivity.hideKeyboardWithInvokable(crossinline invokable: () -> Unit) {
    hideKeyboard()
    getContainerViewTreeObserver().let { treeObserver ->
        treeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (!isKeyboardVisible) {
                    treeObserver.removeOnGlobalLayoutListener(this)
                    invokable.invoke()
                }
            }
        })
    }
}

inline fun BaseActivity.invokeOrHideKeyboardWithInvokable(crossinline invokable: () -> Unit) {
    if (!isKeyboardVisible) {
        invokable.invoke()
    } else {
        hideKeyboardWithInvokable(invokable)
    }
}
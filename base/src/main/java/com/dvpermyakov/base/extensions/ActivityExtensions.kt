package com.dvpermyakov.base.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

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
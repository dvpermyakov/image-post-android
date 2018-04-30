package com.dvpermyakov.base.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

abstract class DialogFragmentWithContext : DialogFragment() {
    val argumentsOrBundle: Bundle
        get() = arguments ?: Bundle()

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let {
            return onCreateDialog(savedInstanceState, it)
        } ?: throw IllegalStateException("DialogFragmentWithContext $this doesn't have context")
    }

    abstract fun onCreateDialog(savedInstanceState: Bundle?, context: Context): Dialog
}
package com.dvpermyakov.base.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import org.jetbrains.anko.bundleOf

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

class ProgressDialogFragment : DialogFragmentWithContext() {
    override fun onCreateDialog(savedInstanceState: Bundle?, context: Context): Dialog {
        argumentsOrBundle.let { args ->
            return MaterialDialog.Builder(context)
                    .content(args.getInt(KEY_MESSAGE))
                    .cancelable(false)
                    .progress(true, 0)
                    .show()
        }
    }

    companion object {
        private const val KEY_MESSAGE = "message"

        fun newInstance(@StringRes messageId: Int) = ProgressDialogFragment().apply {
            arguments = bundleOf(KEY_MESSAGE to messageId)
        }
    }
}
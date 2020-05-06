package com.dvpermyakov.imagepostapplication.fragments

import android.os.Bundle
import androidx.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.dvpermyakov.base.extensions.isPermissionGranted
import com.dvpermyakov.base.extensions.openApplicationSettings
import com.dvpermyakov.base.extensions.requestPermission
import com.dvpermyakov.base.extensions.shouldShowRequestPermissionDetails
import com.dvpermyakov.base.fragments.BaseMoxyFragment
import com.dvpermyakov.imagepostapplication.R
import com.dvpermyakov.imagepostapplication.presenters.PermissionPresenter
import com.dvpermyakov.imagepostapplication.views.PermissionView
import kotlinx.android.synthetic.main.fragment_permission.*
import org.jetbrains.anko.bundleOf

/**
 * Created by dmitrypermyakov on 30/04/2018.
 */

class PermissionFragment : BaseMoxyFragment(), PermissionView {
    private val permission by lazy { arguments?.getString(KEY_PERMISSION).orEmpty() }
    private val message by lazy { arguments?.getInt(KEY_MESSAGE) ?: 0 }

    @InjectPresenter
    lateinit var presenter: PermissionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.setText(message)

        cancelButtonView.setOnClickListener {
            presenter.onCancelClick()
        }

        okButtonView.setOnClickListener {
            if (baseActivity.shouldShowRequestPermissionDetails(permission)) {
                baseActivity.openApplicationSettings()
            } else {
                requestPermission(permission, REQUEST_CODE_PERMISSION)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (baseActivity.isPermissionGranted(permission)) {
            view?.post { showPreviousScreen() }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            showPreviousScreen()
        }
    }

    override fun showPreviousScreen() {
        baseActivity.onBackPressed()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 3514

        private const val KEY_MESSAGE = "message"
        private const val KEY_PERMISSION = "permission"

        fun newInstance(@StringRes message: Int, permission: String) = PermissionFragment().apply {
            arguments = bundleOf(KEY_MESSAGE to message, KEY_PERMISSION to permission)
        }
    }
}
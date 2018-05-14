package com.dvpermyakov.imagepostapplication.fragments

import android.os.Bundle
import android.support.annotation.StringRes
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
    @InjectPresenter
    lateinit var presenter: PermissionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            textView.setText(args.getInt(KEY_MESSAGE))
        }

        cancelButtonView.setOnClickListener {
            presenter.onCancelClick()
        }

        okButtonView.setOnClickListener {
            if (baseActivity.shouldShowRequestPermissionDetails(getPermission())) {
                baseActivity.openApplicationSettings()
            } else {
                requestPermission(getPermission(), REQUEST_CODE_PERMISSION)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (baseActivity.isPermissionGranted(getPermission())) {
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

    private fun getPermission() = arguments?.getString(KEY_PERMISSION).orEmpty()

    companion object {
        private const val REQUEST_CODE_PERMISSION = 3514

        private const val KEY_MESSAGE = "message"
        private const val KEY_PERMISSION = "permission"

        fun newInstance(@StringRes message: Int, permission: String) = PermissionFragment().apply {
            arguments = bundleOf(KEY_MESSAGE to message, KEY_PERMISSION to permission)
        }
    }
}
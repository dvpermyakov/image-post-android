package com.dvpermyakov.base.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvpermyakov.base.activities.BaseActivity

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

abstract class BaseFragment : Fragment() {
    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

    protected abstract val contentResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentResId, container, false)
    }

}
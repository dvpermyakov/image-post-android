package com.dvpermyakov.imagepostapplication.fragments

import com.dvpermyakov.base.fragments.BaseFragment
import com.dvpermyakov.imagepostapplication.R

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

class CreateImagePostFragment : BaseFragment() {
    override val contentResId = R.layout.fragment_image_post

    companion object {
        fun newInstance() = CreateImagePostFragment()
    }
}
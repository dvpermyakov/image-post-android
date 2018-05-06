package com.dvpermyakov.imagepostapplication.activities

import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.imagepostapplication.fragments.CreateImagePostFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

/* todo:

    3) ColoredEditText has colored padding which may overlay text.
    8) Saved image has quality worse than original.

 */

class MainActivity : BaseActivity() {
    override fun createFragment() = CreateImagePostFragment.newInstance()
}
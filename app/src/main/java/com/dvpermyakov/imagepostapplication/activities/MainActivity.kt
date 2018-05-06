package com.dvpermyakov.imagepostapplication.activities

import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.imagepostapplication.fragments.CreateImagePostFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

/* todo:

    1) PostView can be hosted in Scroll View.
    2) Loading dialogs don't save their state.
    3) ColoredEditText has colored padding which may overlay text.
    4) Saved image with linear shader has quality worse than original.

 */

class MainActivity : BaseActivity() {
    override fun createFragment() = CreateImagePostFragment.newInstance()
}
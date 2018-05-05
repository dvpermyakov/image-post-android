package com.dvpermyakov.imagepostapplication.activities

import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.imagepostapplication.fragments.CreateImagePostFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

/* todo:

    3) ColoredEditText has colored padding which may overlay text.
    4) Lack of Rotation gesture detection.
    5) Scale gesture detection doesn't interact with Movement gesture detection.
    8) Saved image has quality worse than original.
    9) Not all steps are implemented in read permission request.

 */

class MainActivity : BaseActivity() {
    override fun createFragment() = CreateImagePostFragment.newInstance()
}
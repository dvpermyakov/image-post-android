package com.dvpermyakov.imagepostapplication.activities

import com.dvpermyakov.base.activities.BaseActivity
import com.dvpermyakov.imagepostapplication.fragments.CreateImagePostFragment

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

/* todo:

    1) StickerListFragment should have landscape layout. Height is hardcoded now.
    2) Thumb image may disappear after recycler view scrolling.
    3) ColoredEditText has colored padding which may overlay text.
    4) Lack of Rotation gesture detection.
    5) Scale gesture detection doesn't interact with Movement gesture detection.
    6) Sticker should be removed in case it is above trash image view.
    7) Sticker will be removed if it intersects boundaries not if motion event intersects boundaries.

 */


class MainActivity : BaseActivity() {
    override fun createFragment() = CreateImagePostFragment.newInstance()
}
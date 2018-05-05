package com.dvpermyakov.base.models

import android.support.annotation.AnimRes
import com.dvpermyakov.base.R

/**
 * Created by dmitrypermyakov on 05/05/2018.
 */

data class AnimationConfig(
        @AnimRes val enter: Int,
        @AnimRes val exit: Int,
        @AnimRes val enterPop: Int,
        @AnimRes val exitPop: Int) {

    companion object {
        fun getBottomAnimationConfig() = AnimationConfig(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter_pop, R.anim.slide_exit_pop)
    }
}
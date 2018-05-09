package com.dvpermyakov.imagepostapplication.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.PointF
import android.view.View
import android.view.animation.DecelerateInterpolator

/**
 * Created by dmitrypermyakov on 09/05/2018.
 */

object AnimatorFactory {
    fun createMovementAnimator(target: View, from: PointF, to: PointF, duration: Long): ObjectAnimator {
        val xMovement = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, from.x, to.x)
        val yMovement = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, from.y, to.y)
        return ObjectAnimator.ofPropertyValuesHolder(target, xMovement, yMovement).apply {
            this.duration = duration
            this.interpolator = DecelerateInterpolator()
        }
    }
}
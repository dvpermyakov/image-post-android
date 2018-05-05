package com.dvpermyakov.imagepostapplication.gestures

import android.content.Context
import android.view.MotionEvent
import com.almeros.android.multitouch.RotateGestureDetector

class UniversalRotateGestureDetector(context: Context, listener: OnRotateGestureListener) : RotateGestureDetector(context, listener) {
    override fun isSloppyGesture(event: MotionEvent) = false
}
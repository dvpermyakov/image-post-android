package com.dvpermyakov.base.extensions

import android.content.res.AssetManager
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import java.io.IOException
import java.io.InputStream

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

fun AssetManager.safetyRead(path: String): String? {
    return try {
        open(path).safetyRead()
    } catch (ioe: IOException) {
        Log.e("AssetManager", "safetyRead", ioe)
        null
    }
}

fun InputStream.safetyRead(): String? {
    return try {
        bufferedReader().use { it.readText() }
    } catch (ioe: IOException) {
        Log.e("InputStream", "safetyRead", ioe)
        null
    }
}

fun MotionEvent.getPointersCenter(): Point {
    var x = 0f
    var y = 0f
    for (index in 0 until pointerCount) {
        x += getX(index)
        y += getY(index)
    }
    return Point((x / pointerCount.toFloat()).toInt(), (y / pointerCount.toFloat()).toInt())
}
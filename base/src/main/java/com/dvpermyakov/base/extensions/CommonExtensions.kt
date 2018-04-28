package com.dvpermyakov.base.extensions

import android.content.res.AssetManager
import android.util.Log
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
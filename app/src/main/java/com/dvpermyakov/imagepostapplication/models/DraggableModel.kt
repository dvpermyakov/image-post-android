package com.dvpermyakov.imagepostapplication.models

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

abstract class DraggableModel(val width: Int, val height: Int) {
    var translationX = .25f       // [0f, 1f]
    var translationY = .25f       // [0f, 1f]
    var scaleFactor = 1f          // [0f, 1f]
    var rotationDegrees = 0f
}
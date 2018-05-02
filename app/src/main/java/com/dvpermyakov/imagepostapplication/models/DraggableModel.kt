package com.dvpermyakov.imagepostapplication.models

/**
 * Created by dmitrypermyakov on 01/05/2018.
 */

abstract class DraggableModel {
    var translationX = .25f  // [0f, 1f]
    var translationY = .25f  // [0f, 1f]
    var scaleX = 1f          // [0f, 1f]
    var scaleY = 1f          // [0f, 1f]
}
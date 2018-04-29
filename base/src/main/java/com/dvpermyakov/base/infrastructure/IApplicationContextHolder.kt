package com.dvpermyakov.base.infrastructure

import android.content.Context

/**
 * Created by dmitrypermyakov on 29/04/2018.
 */

interface IApplicationContextHolder {
    fun getContext(): Context
}
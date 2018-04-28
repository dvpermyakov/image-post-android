package com.dvpermyakov.base.ioc

import io.michaelrocks.lightsaber.Injector

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

interface IInjectorHolder {
    fun getInjector(): Injector
}

interface IEnrichableItem {
    fun bindInjector(holder: IInjectorHolder)
    fun unbindInjector()
}
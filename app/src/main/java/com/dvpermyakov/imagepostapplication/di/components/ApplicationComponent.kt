package com.dvpermyakov.imagepostapplication.di.components

import android.content.Context
import com.dvpermyakov.imagepostapplication.di.modules.InfrastructureModule
import io.michaelrocks.lightsaber.Component
import io.michaelrocks.lightsaber.Provides
import javax.inject.Singleton

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@Component
@Singleton
class ApplicationComponent(private var applicationContext: Context) {
    @Provides
    fun getAppInfrastructureModule() = InfrastructureModule(applicationContext)
}
package com.dvpermyakov.imagepostapplication.di.modules

import android.content.Context
import android.content.res.AssetManager
import com.dvpermyakov.base.infrastructure.IApplicationContextHolder
import io.michaelrocks.lightsaber.Module
import io.michaelrocks.lightsaber.Provides
import javax.inject.Singleton

/**
 * Created by dmitrypermyakov on 28/04/2018.
 */

@Singleton
@Module
class InfrastructureModule(private val applicationContext: Context) {
    @Singleton
    @Provides
    fun getAssetManager(): AssetManager = applicationContext.assets

    @Singleton
    @Provides
    fun getApplicationContextHolder(): IApplicationContextHolder = object : IApplicationContextHolder {
        override fun getContext() = applicationContext
    }
}
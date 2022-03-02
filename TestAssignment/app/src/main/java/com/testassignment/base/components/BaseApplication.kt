package com.testassignment.base.components

import android.app.Application
import com.testassignment.base.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(getKoinModules())
            if (isDebug()) printLogger(Level.DEBUG)
        }
    }

    abstract fun isDebug(): Boolean
    open fun getKoinModules() = mutableListOf(appModule)

}
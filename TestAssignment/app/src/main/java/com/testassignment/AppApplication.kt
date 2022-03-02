package com.testassignment

import com.testassignment.base.components.BaseApplication

class AppApplication:BaseApplication() {
    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }
    companion object{
        @get:Synchronized
        var instance:AppApplication?=null
    }
}
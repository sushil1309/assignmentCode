package com.testassignment.base.data.repo

import com.testassignment.base.data.global.Globals
import com.testassignment.base.data.sharedpref.AppSharedPreferences

/**
 * The abstract Implementation of all kind of data management class which provide all supported type of data provider.
 */
interface RepoManager {
    fun <T> getApi(baseUrl:String,apiClass: Class<T>):T

    fun getSharedPreferences() : AppSharedPreferences

    fun getGlobals() : Globals
}
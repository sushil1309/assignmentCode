package com.testassignment.base.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.testassignment.base.components.BaseViewModelFactory
import com.testassignment.base.data.api.ApiPool
import com.testassignment.base.data.global.AppGlobals
import com.testassignment.base.data.global.Globals
import com.testassignment.base.data.repo.AppRepoManager
import com.testassignment.base.data.repo.RepoManager
import com.testassignment.base.data.sharedpref.AppSharedPreferences
import com.testassignment.base.data.sharedpref.AppSharedPreferencesImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule= module {

    single {
        BaseViewModelFactory(get())
    }
    single {
        //GSON
        GsonBuilder().setLenient().create()
    }
    single<Retrofit.Builder> {
        NetworkService().getRetrofitBuilder(get(), true,get(),androidContext())
    }

    single<Globals> {
        AppGlobals()
    }

    single<RepoManager> {
        // AppRepoManager
        AppRepoManager(get(), get(), get(),get())
    }


    single<AppSharedPreferences> {
        AppSharedPreferencesImpl(
            androidApplication().getSharedPreferences(
                "app",
                Context.MODE_PRIVATE
            )
        )
    }
    single<ApiPool>{
        ApiPool()
    }
}
package com.testassignment.base.di

import android.content.Context
import android.util.Log
import com.testassignment.base.API_TIME_OUT
import com.google.gson.Gson
import com.testassignment.base.data.global.Globals
import com.testassignment.base.data.utils.NetworkInterceptor
import com.testassignment.base.data.utils.RestRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    private fun getClient(isDebug: Boolean, globals: Globals, context:Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(RestRequestInterceptor(globals))
            addInterceptor(NetworkInterceptor(context))
            readTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            callTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
            if (isDebug)
                addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        }.build()

    }

    fun getRetrofitBuilder(gson: Gson, isDebug:Boolean, globals: Globals, context: Context): Retrofit.Builder {
        return Retrofit.Builder()
                .client(getClient(isDebug,globals,context))
                .addConverterFactory(GsonConverterFactory.create(gson))
    }

}
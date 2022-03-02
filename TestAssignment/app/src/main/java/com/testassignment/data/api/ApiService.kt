package com.testassignment.data.api

import com.testassignment.data.model.HomeResponse
import com.testassignment.data.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("home?marketCode=UZ")
    suspend fun getHomeBanner(): Response<HomeResponse>

    @GET("productlist")
    suspend fun getProductList(@Query("page") page: Int,@Query("productTagId")id:Int=13,@Query("marketCode") code:String="UZ"): ProductResponse
}
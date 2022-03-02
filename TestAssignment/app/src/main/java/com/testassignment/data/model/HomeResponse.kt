package com.testassignment.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class HomeResponse {
    @SerializedName("ErrorCode")
    @Expose
     val errorCode: String? = null

    @SerializedName("Message")
    @Expose
     val message: String? = null

    @SerializedName("Data")
    @Expose
     val data: Data? = null
    inner class Data {
        @SerializedName("mainBanner")
        @Expose
        val mainBanner: List<Banner>? = null

        @SerializedName("brandZoneBanner")
        @Expose
        val brandZoneBanner: List<Banner>? = null

        @SerializedName("promotionalBanner")
        @Expose
        val promotionalBanner: List<Banner>? = null

        @SerializedName("promotionalBanner2")
        @Expose
        val promotionalBanner2: List<Banner>? = null

        @SerializedName("recommended")
        @Expose
        val recommended: Recommended? = null

    }
}
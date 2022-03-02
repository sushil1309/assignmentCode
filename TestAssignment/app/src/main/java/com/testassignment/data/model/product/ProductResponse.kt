package com.testassignment.data.model.product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductResponse {
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
        @SerializedName("marketList")
        @Expose
        var marketList: List<Market>? = null

        @SerializedName("Pagination")
        @Expose
        var pagination: Pagination? = null

    }

}
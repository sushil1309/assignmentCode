package com.testassignment.data.model.product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat


class Market {
    @SerializedName("productId")
    @Expose
    val productId: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("localPrice")
    @Expose
    val localPrice: Int? = null

    @SerializedName("imgUrl")
    @Expose
    val imgUrl: String? = null

    @SerializedName("rank")
    @Expose
    val rank: Int? = null

    @SerializedName("ratingEmoji")
    @Expose
    val ratingEmoji: String? = null

    @SerializedName("localCrossedPrice")
    @Expose
    val localCrossedPrice: Int? = null

    @SerializedName("brand")
    @Expose
    val brand: String? = null


}
package com.testassignment.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Recommended {
    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("productTagId")
    @Expose
     val productTagId: Int? = null

}
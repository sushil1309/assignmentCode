package com.testassignment.data.model.product

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Pagination {
    @SerializedName("page")
    @Expose
    val page: Int? = null

    @SerializedName("rowsPerPage")
    @Expose
    val rowsPerPage: Int? = null

    @SerializedName("totalCount")
    @Expose
    val totalCount: Int? = null

    @SerializedName("totalPage")
    @Expose
    val totalPage: Int? = null

}
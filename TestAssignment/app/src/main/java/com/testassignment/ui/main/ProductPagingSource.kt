package com.testassignment.ui.main

import androidx.paging.PagingSource
import com.testassignment.data.api.ApiService
import com.testassignment.data.model.product.Market


class ProductPagingSource(private val apiService: ApiService) : PagingSource<Int, Market>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Market> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getProductList(nextPageNumber)
            LoadResult.Page(
                data = response.data?.marketList?:ArrayList(),
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.data?.pagination?.totalPage?:0) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }



}
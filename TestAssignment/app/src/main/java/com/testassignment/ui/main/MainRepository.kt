package com.testassignment.ui.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.testassignment.base.components.BaseRepository
import com.testassignment.base.components.util.ApiLoadingInteraction
import com.testassignment.base.data.model.ApiResponse
import com.testassignment.data.api.ApiService
import com.testassignment.data.model.HomeResponse
import com.testassignment.data.model.product.Market
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow


class MainRepository(
    private val api: ApiService,
    private val apiLoadingInteraction: ApiLoadingInteraction
) : BaseRepository(apiLoadingInteraction) {

    suspend fun getBanner(): ApiResponse<HomeResponse> {
        return apiOutputHit(
            call = { api.getHomeBanner() }
        )
    }

    suspend fun getMarket(coroutineScope: CoroutineScope): Flow<PagingData<Market>> {
        return Pager(PagingConfig(pageSize = 10)){
            ProductPagingSource(api)
        }.flow.cachedIn(coroutineScope)
    }

}
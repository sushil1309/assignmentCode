package com.testassignment.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.testassignment.R
import com.testassignment.base.components.ApiState
import com.testassignment.base.components.State
import com.testassignment.base.data.repo.RepoManager
import com.testassignment.data.model.Banner
import com.testassignment.data.model.product.Market
import com.testassignment.ui.base.BaseViewModelMVVM
import com.testassignment.ui.main.adapter.BannerRVAdapter
import com.testassignment.ui.main.adapter.MarketRVAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class MainViewModel(repoManager: RepoManager) : BaseViewModelMVVM(repoManager) {
    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }
    private val mainRepository by lazy {
        MainRepository(networkApiInterface, this)
    }

    private var adapter: BannerRVAdapter? = null
    val bannerImageList = MutableLiveData<List<Banner>>()
    private var marketAdapter: MarketRVAdapter? = null


    init {
        adapter = BannerRVAdapter(R.layout.item_banner)
        marketAdapter = MarketRVAdapter(R.layout.item_product)
    }

    fun getHomeBanner() {
        val list: ArrayList<Banner> = ArrayList()
        coroutineScope.launch {
            kotlin.runCatching {
                mainRepository.getBanner()
            }.onSuccess { response ->
                debugLog("Response code ${response.code}")
                if (response.code == -1) {
                    response.data?.let {
                        if (!it.data?.mainBanner.isNullOrEmpty()) {
                            it.data?.mainBanner?.let { _list ->
                                setBannerAdapter(_list)
                                list.also {
                                    it.addAll(_list)
                                    bannerImageList.postValue(_list)
                                }
                            }

                        }
                    }
                }
            }
                .onFailure {
                    handleError(it, ApiState(State.ERROR))
                }
        }

    }

    fun getMarketList() {
        coroutineScope.launch {
            kotlin.runCatching {
                mainRepository.getMarket(this)
            }.onSuccess { response ->
                response.collectLatest { pagedData ->
                    setMarketAdapter(pagedData)
                }
            }.onFailure {
                handleError(it, ApiState(State.ERROR))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setMarketAdapter(pagedData: PagingData<Market>) {

        coroutineScope.launch(Dispatchers.Main) {
            marketAdapter?.submitData(pagedData)
            marketAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setBannerAdapter(bannerList: List<Banner>) {
        adapter?.addBanners(bannerList)
        coroutineScope.launch(Dispatchers.Main) {
            adapter?.notifyDataSetChanged()
        }
    }

    fun getAdapter(): BannerRVAdapter? {
        return adapter
    }

    fun getMarketAdapter(): MarketRVAdapter? {
        return marketAdapter
    }

}
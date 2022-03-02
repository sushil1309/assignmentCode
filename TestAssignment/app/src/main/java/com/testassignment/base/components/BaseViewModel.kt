package com.testassignment.base.components

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.testassignment.base.components.util.ApiLoadingInteraction
import com.testassignment.base.components.util.LiveMessageEvent
import com.testassignment.base.components.util.ViewMessage
import com.testassignment.base.components.util.ViewNavigation
import com.testassignment.base.data.repo.RepoManager

abstract class BaseViewModel(private val repoManager: RepoManager) : ViewModel(),
    ApiLoadingInteraction {

    private val TAG: String=BaseViewModel::class.java.name

    /**
     * viewState Mutable Live Data which emit the state of View from ViewModel
     */
    val mViewState = MutableLiveData<ApiState>()
    val mErrorState = MutableLiveData<String>()
    val navigationEvent = LiveMessageEvent<ViewNavigation>()
    val messagesEvent = LiveMessageEvent<ViewMessage>()
    /**
     * set View State from ViewModel
     */
    protected fun setViewState(newState: ApiState) {
        mViewState.postValue(newState)
    }

    /**
     * add View State Change Listener to viewState and start observing by the host
     */
    fun addViewStateChangeListener(lifecycleOwner: LifecycleOwner, observer: Observer<ApiState>) {
        mViewState.observe(lifecycleOwner, observer)
    }

    override fun isLoadingState(newState: ApiState) {
        setViewState(newState)
    }

    /**
     * Returns Repo Manager
     */
    protected fun getRepoManager(): RepoManager {
        return repoManager
    }

    protected fun debugLog(msg:String){
        Log.d(TAG,msg)
    }

    override fun onCleared() {
        super.onCleared()
    }



}
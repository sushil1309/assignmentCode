package com.testassignment.ui.base


import com.testassignment.base.components.ApiState
import com.testassignment.base.components.BaseViewModel
import com.testassignment.base.components.State
import com.testassignment.base.data.model.ApiResponse
import com.testassignment.base.data.repo.RepoManager
import com.testassignment.base.data.utils.NoInternetException
import com.testassignment.data.api.ApiService

open class BaseViewModelMVVM(repoManager: RepoManager): BaseViewModel(repoManager) {
    protected val networkApiInterface by lazy {
        getRepoManager().getApi("http://qvr9g.mocklab.io/", ApiService::class.java)
    }

    protected fun <T : Any> handleError(output: ApiResponse<T>, apiState: ApiState) {
        setViewState(apiState)
    }

    protected fun  handleError(output:Throwable,apiState: ApiState) {
        when (output) {
            is NoInternetException -> {
                apiState.state = State.OFFLINE
                apiState.msg = "No internet, Please try again"
                setViewState(apiState)
            }
            else -> setViewState(apiState)
        }
    }
}

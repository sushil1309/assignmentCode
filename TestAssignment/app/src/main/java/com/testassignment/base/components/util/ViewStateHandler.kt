package com.testassignment.base.components.util

import com.testassignment.base.components.ApiState
import com.testassignment.base.components.State

internal object ViewStateHandler {
    fun handleState(baseViewImpl: BaseViewImpl, newState: ApiState){
        when(newState.state){
            State.ERROR->{
                baseViewImpl.apply {
                    hideLoading()
                    newState.msg.let {
                        showError(it)
                    }
                }
            }
            State.OFFLINE->{
                baseViewImpl.apply {
                    hideLoading()
                    hideError()
                    noIntenet()
                }
            }
            State.IDLE->{
                baseViewImpl.apply {
                    hideError()
                    hideLoading()
                }
            }

            State.LOADING->{
                baseViewImpl.apply {
                    hideError()
                    showLoading()
                }
            }
        }
    }
}
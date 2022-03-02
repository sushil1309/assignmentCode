package com.testassignment.base.components.util

import com.testassignment.base.components.ApiState

interface ApiLoadingInteraction {
    fun isLoadingState(newState: ApiState)
}
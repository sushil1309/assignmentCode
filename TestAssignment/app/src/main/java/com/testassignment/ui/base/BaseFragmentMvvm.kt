package com.testassignment.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.testassignment.base.components.BaseFragment


abstract class BaseFragmentMvvm<T : ViewDataBinding, V : BaseViewModelMVVM> : BaseFragment<T, V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
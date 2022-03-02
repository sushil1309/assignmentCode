package com.testassignment.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

import androidx.databinding.ViewDataBinding
import com.testassignment.base.components.BaseActivity
import com.testassignment.base.components.BaseViewModel


abstract class BaseActivityMVVM<T:ViewDataBinding, V: BaseViewModel>: BaseActivity<T, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setActionBar(toolbar: Toolbar, title:String?=null, isBackButtonNotSet:Boolean=true){
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            _actionbar->
            _actionbar.setDisplayHomeAsUpEnabled(isBackButtonNotSet)
            _actionbar.title=title?:""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let {
            return when (it.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> return false
            }
        }
    }
}
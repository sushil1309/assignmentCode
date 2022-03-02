package com.testassignment.base.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.testassignment.R

import com.testassignment.base.components.util.BaseViewImpl
import com.testassignment.base.components.util.ViewMessage
import com.testassignment.base.components.util.ViewNavigation
import com.testassignment.base.components.util.ViewStateHandler
import com.testassignment.base.dialog.CustomProgressDialog
import org.koin.android.ext.android.inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    ViewNavigation, BaseViewImpl, ViewMessage {

    private val TAG=BaseActivity::class.java.name
    private var mProgressDialog: CustomProgressDialog? = null
    protected var mViewDataBinding: T? = null
    var mViewModel: V? = null

    abstract val bindingVariable: Int

    abstract val layoutId: Int

    abstract val viewModelClass: Class<V>

    private val viewModelFactory: BaseViewModelFactory by inject()

    var isNetworkConnected: Boolean = true

    fun getViewModel() = mViewModel

    override fun <T> startActivityForResult(cls: Class<T>, requestCode: Int, extras: Bundle?) {
        val resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
                if (result.resultCode==Activity.RESULT_OK){
                    val data:Intent?=result.data
                }

        }
        Intent(this, cls).apply {
            if (extras != null)
                putExtras(extras)
            resultLauncher.launch(intent)
        }

    }

    override fun <T> openActivity(cls: Class<T>, extras: Bundle?) {
        Intent(this, cls).apply {
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
    }

    override fun <T> openActivitynFinish(cls: Class<T>, extras: Bundle?) {
        Intent(this, cls).apply {
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
        finish()
    }

    override fun closeActivity() {
        finish()
    }

    override fun closeActivityWithResult(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    override fun <T> openActivitynClearTask(cls: Class<T>, extras: Bundle?) {
        Intent(this, cls).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
    }

    override fun openDialog(title: String, msg: String, type: String) {

    }

    /**
     * shows A Long Toast Showing the msg on It
     *
     * @param msg the Message
     */
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun openActivityOnTokenExpire() { // redirect to login

    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CustomProgressDialog(this)
        mProgressDialog?.show()
    }

    override fun hideLoading() {
        mProgressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    override fun showError(msg: String) {
        showToast(msg)
    }

    override fun hideError() {

    }

    override fun noIntenet() {
        showToast(getString(R.string.no_internet_msg))
    }

    private fun onViewStateChanged(newState: ApiState) {
        ViewStateHandler.handleState(this, newState)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        getViewModel()?.messagesEvent?.setEventReceiver(this, this)
        getViewModel()?.navigationEvent?.setEventReceiver(this, this)
    }

    private fun performDataBinding() {
        Log.d(TAG, "performDataBinding $layoutId")
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
//        mViewModel = if (mViewModel == null) {
//            ViewModelProvider(this,viewModelFactory).get(viewModelClass)
//        }
//        else mViewModel

        mViewModel = mViewModel ?: ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.executePendingBindings()
        mViewModel?.mViewState?.observe(this, Observer<ApiState> { state ->
            onViewStateChanged(state)
        })

    }

    override fun showMessage(msg: String) {
        showToast(msg)
    }
}
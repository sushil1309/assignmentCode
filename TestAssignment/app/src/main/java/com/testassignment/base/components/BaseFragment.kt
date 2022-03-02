package com.testassignment.base.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.testassignment.R

import com.testassignment.base.components.util.BaseViewImpl
import com.testassignment.base.components.util.ViewMessage
import com.testassignment.base.components.util.ViewNavigation
import com.testassignment.base.components.util.ViewStateHandler
import com.testassignment.base.dialog.CustomProgressDialog
import org.koin.android.ext.android.inject

abstract class BaseFragment<T:ViewDataBinding, V: BaseViewModel>:Fragment(), ViewMessage,
    BaseViewImpl, ViewNavigation {

    private var mRootView: View? = null
    var mViewDataBinding: T? = null

    private var mProgressDialog: CustomProgressDialog? = null

    private var mViewModel: V? = null
    private val viewModelFactory: BaseViewModelFactory by inject()


    abstract val bindingVariable: Int

    var isNetworkConnected: Boolean = true

    abstract val layoutId: Int


    abstract val viewModelClass: Class<V>

    fun getViewModel() = mViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.messagesEvent?.setEventReceiver(this, this)
        getViewModel()?.navigationEvent?.setEventReceiver(this, this)
    }

    private fun performDataBinding() {

        this.mViewModel = mViewModel ?: ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.executePendingBindings()
        mViewModel?.mViewState?.observe(viewLifecycleOwner,
            Observer<ApiState> { t ->
                onViewStateChanged(t)
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = mViewDataBinding?.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.executePendingBindings()
        performDataBinding()
    }

    private fun onViewStateChanged(newState: ApiState) {
        ViewStateHandler.handleState(this, newState)
    }
    override fun onDetach() {
        super.onDetach()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }

    override fun showMessage(msg: String) {
        showToast(msg)
    }

    override fun showLoading() {
        hideLoading()
        if(isActivityInForeground()) {
            mProgressDialog = activity?.let { CustomProgressDialog(it) };
            mProgressDialog?.show()
        }
    }

    override fun hideLoading() {

    }

    override fun showError(msg: String) {
        TODO("Not yet implemented")
    }

    override fun hideError() {
        TODO("Not yet implemented")
    }
    /**
     * shows A Long Toast Showing the msg on It
     *
     * @param msg the Message
     */
    private fun showToast(msg: String) {
        Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
    }
    override fun noIntenet() {
        showToast(getString(R.string.no_internet_msg))
    }

    override fun <T> startActivityForResult(cls: Class<T>, requestCode: Int, extras: Bundle?) {

    }

    override fun <T> openActivity(cls: Class<T>, extras: Bundle?) {
        Intent(activity, cls).apply {
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
    }

    override fun <T> openActivitynFinish(cls: Class<T>, extras: Bundle?) {
        Intent(activity, cls).apply {
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }
        activity?.finish()
    }

    override fun closeActivity() {
        activity?.finish()
    }

    override fun closeActivityWithResult(resultCode: Int) {
        activity?.setResult(resultCode)
        activity?.finish()
    }

    override fun <T> openActivitynClearTask(cls: Class<T>, extras: Bundle?) {
        Intent(activity, cls).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (extras != null)
                putExtras(extras)
            startActivity(this)
        }

    }

    override fun openDialog(title: String, msg: String, type: String) =Unit

    //to check activity in background or not
    protected fun isActivityInForeground():Boolean = (activity != null && isAdded)
}
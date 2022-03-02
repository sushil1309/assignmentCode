package com.testassignment.ui.main


import android.os.Bundle
import android.util.Log
import com.testassignment.BR
import com.testassignment.R
import com.testassignment.data.model.Banner
import com.testassignment.databinding.ActivityMainBinding
import com.testassignment.ui.base.BaseActivityMVVM
import java.util.*

class MainActivity : BaseActivityMVVM<ActivityMainBinding, MainViewModel>() {
    private var mTimer = Timer()
    // Declare globally
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.getHomeBanner()
        getViewModel()?.getMarketList()
        setUpObservers()
    }

    private fun setUpObservers() {
        getViewModel()?.bannerImageList?.observe(this,{
            scheduleTimer(it)
        })
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onPause() {
        mTimer.cancel()
        mTimer.purge()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Main","On resume called")
    }

    override fun onRestart() {
        super.onRestart()
        scheduleTimer(getViewModel()?.bannerImageList?.value)
    }
    private fun scheduleTimer(list: List<Banner>?){

        list?.let {
            /**
             * This timer will call each of the seconds.
             */
            mTimer= Timer()
            mTimer.schedule(object : TimerTask() {
                override fun run() {
                    mViewDataBinding?.rvBanner?.smoothScrollToPosition(position)
                    // increase your position so new image will show
                    position++
                    // check whether position increased to length then set it to 0
                    // so it will show images in
                    if (position >= it.size) position = 0
                    // Set Image


                }
            }, 0, 3000)
        }
    }
}
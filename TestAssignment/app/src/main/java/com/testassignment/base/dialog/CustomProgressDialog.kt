package com.testassignment.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.testassignment.R


class CustomProgressDialog(context: Context):Dialog(context, R.style.TransparentProgressDialog) {

    override fun show() {
        super.show()
    }

    override fun hide() {
        super.hide()
    }

    init {
        val wlmp = window?.attributes
        wlmp?.gravity = Gravity.CENTER
        window?.attributes = wlmp
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        setContentView(R.layout.custom_progress)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
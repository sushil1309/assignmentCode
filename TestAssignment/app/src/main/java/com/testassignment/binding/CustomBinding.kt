package com.testassignment.binding

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.testassignment.R
import java.text.DecimalFormat

class CustomBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(url: String) {
            Log.d("CustomBinding"," url $url")
            Glide.with(this).load(url).placeholder(R.mipmap.ic_launcher).error(
                R.mipmap.ic_launcher
            ).into(this)
        }
        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter("displayPrice")
        fun TextView.currencyFormat(amount: String) {
            val formatter = DecimalFormat("###,###,##0")
            this.text="UZS "+formatter.format(amount.toDouble()).toString()

        }
    }
}
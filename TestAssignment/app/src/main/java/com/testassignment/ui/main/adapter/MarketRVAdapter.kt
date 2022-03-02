package com.testassignment.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testassignment.BR
import com.testassignment.data.model.product.Market

class MarketRVAdapter(@param:LayoutRes private val layoutId: Int):
    PagingDataAdapter<Market, MarketRVAdapter.MarketViewHolder>(MarketsComparator) {
    private fun getLayoutForPosition(position: Int):Int{
        return layoutId
    }

    object MarketsComparator : DiffUtil.ItemCallback<Market>() {
        override fun areItemsTheSame(oldItem: Market, newItem: Market): Boolean {
            return oldItem.productId == newItem.productId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Market, newItem: Market): Boolean {
            return oldItem == newItem
        }
    }
    inner class MarketViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Market) {
            binding.setVariable(BR.market,obj)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { holder.bind(it) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,viewType,parent,false)
        return MarketViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutForPosition(position)
    }


}
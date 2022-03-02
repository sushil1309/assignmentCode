package com.testassignment.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.testassignment.data.model.Banner
import com.testassignment.BR

class BannerRVAdapter(@param:LayoutRes private val layoutId: Int) :
    RecyclerView.Adapter<BannerRVAdapter.GenericViewHolder>() {

    private val arrayList: ArrayList<Banner> = ArrayList()

    private fun getLayoutForPosition(position: Int):Int{
        return layoutId
    }

    private fun getObjForPosition(position: Int):Banner{
    return arrayList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,viewType,parent,false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val obj=getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutForPosition(position)
    }

    fun addBanners(mainBanner: List<Banner>?) {
        this.arrayList.apply {
            clear()
            if (mainBanner != null) {
                addAll(mainBanner)
            }
        }
    }

    inner class GenericViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Banner) {
            binding.setVariable(BR.obj,obj)
            binding.executePendingBindings()
        }
    }
}
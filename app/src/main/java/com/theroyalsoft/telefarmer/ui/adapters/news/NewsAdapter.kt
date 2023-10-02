package com.theroyalsoft.telefarmer.ui.adapters.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemNewsHeadBinding
import com.theroyalsoft.telefarmer.databinding.ItemNewsLayoutBinding

/**
 * Created by Pritom Dutta on 20/5/23.
 */
class NewsAdapter(val onNewsSelect: () -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return VIEW_TYPE_HEADER
        return VIEW_TYPE_NEWS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_HEADER -> {
                val item = ItemNewsHeadBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return HeaderHolder(item)
            }
            else -> {
                val item = ItemNewsLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return NewsHolder(item)
            }
        }
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class HeaderHolder(private val mBinding: ItemNewsHeadBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    inner class NewsHolder(private val mBinding: ItemNewsLayoutBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onNewsSelect() }
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_NEWS = 1
    }
}
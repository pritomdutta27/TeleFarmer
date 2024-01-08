package com.theroyalsoft.telefarmer.ui.adapters.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.home.NewsModel
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemNewsHeadBinding
import com.theroyalsoft.telefarmer.databinding.ItemNewsLayoutBinding
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 20/5/23.
 */
class NewsAdapter(val onNewsSelect: (data: NewsModel) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var list: List<NewsModel> = emptyList()
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

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<NewsModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class HeaderHolder(private val mBinding: ItemNewsHeadBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onNewsSelect(list[position]) }
            mBinding.apply {
                val urlImg = LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + list[position]?.imageUrl
                Log.e("urlImg", "onBind: "+urlImg )
                imgNews.setImage(urlImg)
                tvNewTitle.text = list[position]?.titleBn ?: ""
                tvNewDate.text = list[position]?.dateAndTime
            }
        }
    }

    inner class NewsHolder(private val mBinding: ItemNewsLayoutBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onNewsSelect(list[position]) }
            mBinding.apply {
                val urlImg = LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + list[position]?.imageUrl
                Log.e("urlImg", "onBind: "+urlImg )
                imgNews.setImage(urlImg)
                tvNewTitle.text = list[position]?.titleBn ?: ""
                tvNewDate.text = list[position]?.dateAndTime
            }
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_NEWS = 1
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.tipsntricks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.home.NewsModel
import com.farmer.primary.network.model.home.TipsCategory
import com.farmer.primary.network.model.home.TricksTip
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemGridTipsNTricksBinding
import com.theroyalsoft.telefarmer.databinding.ItemTipsNTricksBinding
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 21/10/23.
 */

class TipsNTricksHomeAdapter(private val onClick: (data:TipsCategory) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<TipsCategory> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemGridTipsNTricksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TipsAndTricksHomeHolder(item)
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<TipsCategory>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class TipsAndTricksHomeHolder(private val mBinding: ItemGridTipsNTricksBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onClick.invoke(list[position]) }
            mBinding.apply {
                val urlImg = LocalData.getMetaInfoMetaData().imgBaseUrl + "/uploaded/" + list[position]?.imageUrl
                imgTitlePack.setImage(urlImg)
                tvTrips.text = list[position].nameBn
            }
        }
    }
}
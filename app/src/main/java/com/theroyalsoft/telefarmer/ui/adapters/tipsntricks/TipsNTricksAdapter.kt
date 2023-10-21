package com.theroyalsoft.telefarmer.ui.adapters.tipsntricks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.model.home.NewsModel
import com.farmer.primary.network.model.home.TricksTip
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemTipsNTricksBinding
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 20/5/23.
 */
class TipsNTricksAdapter(val onTipsItemSelect: (data: TricksTip) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<TricksTip> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemTipsNTricksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TipsAndTricksHolder(item)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun submitData(list: List<TricksTip>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class TipsAndTricksHolder(private val mBinding: ItemTipsNTricksBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onTipsItemSelect(list[position]) }
            mBinding.apply {
                imgNews.setImage(list[position].imageUrl)
                tvNewTitle.text = list[position].title
            }
        }
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.tipsntricks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemTipsNTricksBinding

/**
 * Created by Pritom Dutta on 20/5/23.
 */
class TipsNTricksAdapter(val onTipsItemSelect: () -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemTipsNTricksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TipsAndTricksHolder(item)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class TipsAndTricksHolder(private val mBinding: ItemTipsNTricksBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onTipsItemSelect() }
        }
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.tipsntricks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemGridTipsNTricksBinding
import com.theroyalsoft.telefarmer.databinding.ItemTipsNTricksBinding

/**
 * Created by Pritom Dutta on 21/10/23.
 */

class TipsNTricksHomeAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemGridTipsNTricksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TipsAndTricksHomeHolder(item)
    }

    override fun getItemCount(): Int = 8

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class TipsAndTricksHomeHolder(private val mBinding: ItemGridTipsNTricksBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }
}
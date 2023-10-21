package com.theroyalsoft.telefarmer.ui.adapters.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemSliderBinding

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class SliderAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return SliderHolder(item)
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class SliderHolder(private val mBinding: ItemSliderBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.previousConsultation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemPreviousConsultationBinding

/**
 * Created by Pritom Dutta on 21/5/23.
 */
class PreviousConsultationAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemPreviousConsultationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PreviousConsultationHolder(item)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class PreviousConsultationHolder(private val mBinding: ItemPreviousConsultationBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }
}
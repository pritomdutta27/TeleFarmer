package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemLoanDetailsBinding
import com.theroyalsoft.telefarmer.model.loan.LoanCostPer

/**
 * Created by Pritom Dutta on 24/11/23.
 */
class LoanDetailsAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private var list: List<LoanCostPer> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemLoanDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return LoanDetailViewHolder(item)
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<LoanCostPer>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class LoanDetailViewHolder(private val mBinding: ItemLoanDetailsBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
//
            mBinding.apply {
                tvTotalTitle.text = list[position].name
                tvTotalLoanAmount.text =  "${list[position].price} টাকা"
            }
        }
    }
}
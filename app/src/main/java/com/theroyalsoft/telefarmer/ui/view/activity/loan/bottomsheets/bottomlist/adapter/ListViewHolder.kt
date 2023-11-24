package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import bio.medico.patient.common.ui.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemListBottomSheetBinding
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack

/**
 * Created by Pritom Dutta on 24/11/23.
 */
class ListViewHolder(inflater: LayoutInflater
) : BaseViewHolder<ItemListBottomSheetBinding>(
binding = ItemListBottomSheetBinding.inflate(inflater)
) {
    fun bind(data: LoanDetailsResponseItem, callback: ListBottomSheetCallBack<LoanDetailsResponseItem>?) {
        itemView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        itemView.setSafeOnClickListener {
            callback?.invoke(data)
        }

        binding.tvListName.text = data.crop_name
    }
}
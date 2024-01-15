package com.theroyalsoft.telefarmer.ui.view.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import bio.medico.patient.common.ui.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemListBottomSheetBinding
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack

/**
 * Created by Pritom Dutta on 17/5/23.
 */
class DivisionViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ItemListBottomSheetBinding>(
    binding = ItemListBottomSheetBinding.inflate(inflater)
) {
    fun bind(divisionDataModel: DivisionDataModel, callback: ListBottomSheetCallBack<DivisionDataModel>?) {
        itemView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        itemView.setSafeOnClickListener {
            callback?.invoke(divisionDataModel)
        }

        binding.tvListName.text = divisionDataModel.bn_name
    }
}
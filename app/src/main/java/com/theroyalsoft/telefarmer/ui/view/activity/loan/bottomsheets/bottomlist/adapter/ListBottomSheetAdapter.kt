package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.bottomlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.common.ui.BaseListAdapter
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack

/**
 * Created by Pritom Dutta on 24/11/23.
 */
class ListBottomSheetAdapter :
    BaseListAdapter<LoanDetailsResponseItem>(
        itemsSame = { old, new -> old.crop_cost == new.crop_cost },
        contentsSame = { old, new -> old == new }
    ) {

    private var callback: ListBottomSheetCallBack<LoanDetailsResponseItem>? = null
    private var unfilteredList: List<LoanDetailsResponseItem> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater, viewType: Int) =
        ListViewHolder(inflater)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                holder.bind(getItem(position), callback)
            }
        }
    }

    fun onClickItem(callback: ListBottomSheetCallBack<LoanDetailsResponseItem>) {
        this.callback = callback
    }

    fun modifyList(list: List<LoanDetailsResponseItem>) {
        unfilteredList = list
        submitList(list)
    }
}
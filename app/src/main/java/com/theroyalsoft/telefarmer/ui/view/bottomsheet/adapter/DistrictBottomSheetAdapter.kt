package live.tech.view.ListBottomSheetWithSearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseListAdapter
import com.theroyalsoft.telefarmer.ui.custom.ListBottomSheetCallBack
import com.theroyalsoft.telefarmer.ui.view.bottomsheet.DivisionDataModel
import com.theroyalsoft.telefarmer.ui.view.bottomsheet.DivisionViewHolder
import java.util.*

/**
 * Created by Pritom Dutta on 17/5/23.
 */

class DistrictBottomSheetAdapter() : BaseListAdapter<DivisionDataModel>(
        itemsSame = { old, new -> old.id == new.id },
        contentsSame = { old, new -> old == new }
    ) {

    private var callback: ListBottomSheetCallBack<DivisionDataModel>? = null
    private var unfilteredList: List<DivisionDataModel> = emptyList()

    fun onClickItem(callback: ListBottomSheetCallBack<DivisionDataModel>) {
        this.callback = callback
    }

    fun modifyList(list: List<DivisionDataModel>) {
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence?) {
        val list = mutableListOf<DivisionDataModel>()

        // perform the data filtering
        if (!query.isNullOrEmpty()) {
            list.addAll(unfilteredList.filter {
                if (it.name != null) {
                    it.name?.lowercase(Locale.getDefault())
                        ?.contains(query.toString().lowercase(Locale.getDefault()))!!
                } else {
                    "${it.postOffice} (${it.postCode})".lowercase().contains(query.toString())!!
                }
            })
        } else {
            list.addAll(unfilteredList)
        }
        submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ) =  DivisionViewHolder(inflater)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DivisionViewHolder -> {
                holder.bind(getItem(position), callback)
            }
        }
    }
}

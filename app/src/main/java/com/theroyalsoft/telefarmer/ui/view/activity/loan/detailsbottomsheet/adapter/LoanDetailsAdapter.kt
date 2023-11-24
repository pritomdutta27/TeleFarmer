package com.theroyalsoft.telefarmer.ui.view.activity.loan.detailsbottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemLoanDetailsBinding

/**
 * Created by Pritom Dutta on 24/11/23.
 */
class LoanDetailsAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemLoanDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return LoanDetailViewHolder(item)
    }

    override fun getItemCount(): Int = 8

//    fun submitData(list: List<Forecastday>) {
//        this.list = list
//        notifyDataSetChanged()
//    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class LoanDetailViewHolder(private val mBinding: ItemLoanDetailsBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
//
//            mBinding.apply {
//                val img = "http:${list[position].day.condition.icon}"
//                imgWeather.setImage(img)
//                tvWeather.text = "${list[position].day.maxtemp_c}°"
//                tvWeatherDate.text = list[position].date.getFromDateTime("yyyy-mm-dd", "dd/mm")
//            }
//            itemView.setOnClickListener { onClickImage() }
        }
    }
}
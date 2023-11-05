package com.theroyalsoft.telefarmer.ui.adapters.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.farmer.primary.network.model.weather.Forecastday
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemWeatherBinding
import com.theroyalsoft.telefarmer.extensions.getFromDateTime
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 21/5/23.
 */

class WeatherAdapter(val onClickImage: () -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<Forecastday> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return WeatherHolder(item)
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<Forecastday>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class WeatherHolder(private val mBinding: ItemWeatherBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {

            mBinding.apply {
                val img = "http:${list[position].day.condition.icon}"
                imgWeather.setImage(img)
                tvWeather.text = "${list[position].day.maxtemp_c}°"
                tvWeatherDate.text = list[position].date.getFromDateTime("yyyy-mm-dd", "dd/mm")
            }
//            itemView.setOnClickListener { onClickImage() }
        }
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmer.primary.network.model.home.NewsModel
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemSliderBinding
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class SliderAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<NewsModel> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return SliderHolder(item)
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<NewsModel>){
        this.list = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class SliderHolder(private val mBinding: ItemSliderBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.imgItem.setImage(list[position].imageUrl)
            mBinding.tvNewTitle.text = list[position].title
            mBinding.tvNewDate.text = list[position].dateAndTime
        }
    }
}
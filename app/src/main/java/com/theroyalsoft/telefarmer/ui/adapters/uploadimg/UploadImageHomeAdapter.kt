package com.theroyalsoft.telefarmer.ui.adapters.uploadimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemImageUploadBinding

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class UploadImageHomeAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemImageUploadBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageUploadHomeHolder(item)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageUploadHomeHolder(private val mBinding: ItemImageUploadBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
//            itemView.setOnClickListener { onClickImage() }
        }
    }
}
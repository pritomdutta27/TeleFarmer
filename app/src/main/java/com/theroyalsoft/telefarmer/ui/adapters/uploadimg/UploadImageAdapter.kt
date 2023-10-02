package com.theroyalsoft.telefarmer.ui.adapters.uploadimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemUploadImageBinding

/**
 * Created by Pritom Dutta on 21/5/23.
 */
class UploadImageAdapter(val onClickImage: () -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemUploadImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageUploadHolder(item)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageUploadHolder(private val mBinding: ItemUploadImageBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            itemView.setOnClickListener { onClickImage() }
        }
    }
}
package com.theroyalsoft.telefarmer.ui.adapters.uploadimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemImageUploadBinding
import com.theroyalsoft.telefarmer.extensions.setImage

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class UploadImageHomeAdapter(private val imgUrl: String) : RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<ResponseLabReport.ItemLabReport> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemImageUploadBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageUploadHomeHolder(item)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageUploadHomeHolder(private val mBinding: ItemImageUploadBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.imgNews.setImage(imgUrl + list[position].fileUrl)
//            itemView.setOnClickListener { onClickImage() }
        }
    }

    fun submitData(list: List<ResponseLabReport.ItemLabReport>) {
        this.list = list
        notifyDataSetChanged()
    }
}
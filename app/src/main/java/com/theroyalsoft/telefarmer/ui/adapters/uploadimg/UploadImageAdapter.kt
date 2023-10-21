package com.theroyalsoft.telefarmer.ui.adapters.uploadimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.farmer.primary.network.dataSource.local.LocalData
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemUploadImageBinding
import com.theroyalsoft.telefarmer.extensions.getFromDateTime
import com.theroyalsoft.telefarmer.extensions.setImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

/**
 * Created by Pritom Dutta on 21/5/23.
 */

class UploadImageAdapter(val onClickImage: () -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<ResponseLabReport.ItemLabReport> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemUploadImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageUploadHolder(item)
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<ResponseLabReport.ItemLabReport>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageUploadHolder(private val mBinding: ItemUploadImageBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
//            itemView.setOnClickListener { onClickImage() }
            mBinding.imgNews.setImage(LocalData.getMetaInfoMetaData().imgBaseUrl + list[position].fileUrl)
            val file = File(list[position].fileUrl)
            mBinding.tvNewTitle.text = file.name
            mBinding.tvNewDate.text = list[position].updatedAt.getFromDateTime("yyyy-MM-dd'T'HH:mm:ssXXX", "MMM dd, yyyy")
        }
    }
}
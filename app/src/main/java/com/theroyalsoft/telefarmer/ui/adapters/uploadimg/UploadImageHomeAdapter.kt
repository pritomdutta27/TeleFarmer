package com.theroyalsoft.telefarmer.ui.adapters.uploadimg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemImageEmptyBinding
import com.theroyalsoft.telefarmer.databinding.ItemImageUploadBinding
import com.theroyalsoft.telefarmer.extensions.setImage
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class UploadImageHomeAdapter(private val imgUrl: String, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private var list: List<ResponseLabReport.ItemLabReport> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 1) {
            val item = ItemImageUploadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return ImageUploadHomeHolder(item)
        }
        //
        val item = ItemImageEmptyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageEmptyHolder(item)
    }

    override fun getItemCount(): Int = if (list.isEmpty()) {
        1
    } else
        list.size

    override fun getItemViewType(position: Int): Int {
        return if (list.isEmpty()) {
            0
        } else
            1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class ImageUploadHomeHolder(private val mBinding: ItemImageUploadBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.imgNews.setImage(imgUrl + "/uploaded/" + list[position].fileUrl)
            mBinding.imgNews.setSafeOnClickListener { onClick(list[position].fileUrl) }
        }
    }

    inner class ImageEmptyHolder(private val mBinding: ItemImageEmptyBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }

    fun submitData(list: List<ResponseLabReport.ItemLabReport>) {
        this.list = list
        notifyDataSetChanged()
    }
}
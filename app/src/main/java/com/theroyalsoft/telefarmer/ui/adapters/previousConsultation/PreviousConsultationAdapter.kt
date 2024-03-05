package com.theroyalsoft.telefarmer.ui.adapters.previousConsultation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemNoPreviousConsultationBinding
import com.theroyalsoft.telefarmer.databinding.ItemPreviousConsultationBinding
import com.theroyalsoft.telefarmer.extensions.getFromDateTime

/**
 * Created by Pritom Dutta on 21/5/23.
 */
class PreviousConsultationAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var listCall: List<ResponseCallHistory> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == 1) {
            val item = ItemPreviousConsultationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return PreviousConsultationHolder(item)
        } else {
            val item = ItemNoPreviousConsultationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return NoPreviousConsultationHolder(item)
        }

    }

    override fun getItemCount(): Int = if (listCall.isEmpty()) {
        1
    } else
        listCall.size

    override fun getItemViewType(position: Int): Int {
        return if (listCall.isEmpty()) {
            0
        } else
            1
    }

    fun submitData(list: List<ResponseCallHistory>) {
        this.listCall = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class PreviousConsultationHolder(private val mBinding: ItemPreviousConsultationBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            mBinding.apply {
                tvDoctorName.text = listCall[position].doctorName
                tvDoctorSpecialist.text = listCall[position].callStatus
                tvDate.text = listCall[position].updatedAt.getFromDateTime(
                    "yyyy-MM-dd'T'HH:mm:ssXXX",
                    "MMM dd, yyyy"
                )
                tvTime.text = listCall[position].updatedAt.getFromDateTime(
                    "yyyy-MM-dd'T'HH:mm:ssXXX",
                    "hh:mm a"
                )
            }
        }
    }

    inner class NoPreviousConsultationHolder(private val mBinding: ItemNoPreviousConsultationBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
        }
    }
}
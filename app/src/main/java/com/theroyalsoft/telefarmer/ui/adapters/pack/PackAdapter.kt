package com.theroyalsoft.telefarmer.ui.adapters.pack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.base.BaseViewHolder
import com.theroyalsoft.telefarmer.databinding.ItemPackBinding
import com.theroyalsoft.telefarmer.extensions.getStringWithTwoArg

/**
 * Created by Pritom Dutta on 20/10/23.
 */
class PackAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val item = ItemPackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PackHolder(item)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class PackHolder(private val mBinding: ItemPackBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {

            val text = itemView.context.getStringWithTwoArg(
                R.string.pack_params,
                "20", "month"
            )
            mBinding.tvPackAmount.text =  HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}
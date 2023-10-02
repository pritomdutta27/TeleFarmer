package com.theroyalsoft.telefarmer.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Pritom Dutta on 20/5/23.
 */
abstract class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    abstract fun onBind(position: Int)
}
package com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Pritom Dutta on 30/1/24.
 */
abstract class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    abstract fun onBind(position: Int)
}
package com.farmer.primary.network.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TricksTip(
    val details: String,
    val imageUrl: String,
    val title: String,
    val category_id: Int
): Parcelable
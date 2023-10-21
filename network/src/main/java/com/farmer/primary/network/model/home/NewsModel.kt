package com.farmer.primary.network.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val dateAndTime: String,
    val details: String,
    val imageUrl: String,
    val title: String
):Parcelable
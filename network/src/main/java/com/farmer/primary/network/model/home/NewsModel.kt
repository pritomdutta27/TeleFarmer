package com.farmer.primary.network.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val dateAndTime: String,
    val uuid: String,
    val detailsEng: String?,
    val detailsBn: String?,
    val imageUrl: String?,
    val titleEng: String?,
    val titleBn: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val isHighlighted: Boolean,
    val isPublished: Boolean,
):Parcelable
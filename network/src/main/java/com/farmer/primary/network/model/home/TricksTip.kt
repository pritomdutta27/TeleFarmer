package com.farmer.primary.network.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TricksTip(
    val uuid: String?,
    val categoryUuid: String?,
    val detailsBn: String?,
    val detailsEng: String?,
    val imageUrl: String,
    val titleEng: String?,
    val titleBn: String?,
    val category_id: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val isHighlighted: Boolean,
    val isPublished: Boolean,
    var catName: String? = "",
): Parcelable
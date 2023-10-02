package com.farmer.primary.network.model.metadata

import com.google.gson.annotations.SerializedName

data class MetaDataResponse(
//    val meta: MetaModel?
    @SerializedName("MetaModel")
    var metaData: MetaModel?
)
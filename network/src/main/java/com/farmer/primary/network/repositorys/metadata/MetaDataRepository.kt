package com.farmer.primary.network.repositorys.metadata

import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 13/1/23.
 */
interface MetaDataRepository {
    suspend fun fetchMetaData(): NetworkResult<Map<String, MetaModel>>
}
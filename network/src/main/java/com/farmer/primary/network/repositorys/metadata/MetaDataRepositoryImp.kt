package com.farmer.primary.network.repositorys.metadata

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 13/1/23.
 */

class MetaDataRepositoryImp @Inject constructor(private val api: FarmerApi) : MetaDataRepository {
    override suspend fun fetchMetaData(): NetworkResult<Map<String, MetaModel>> {
       return api.getMeta()
    }
}
package com.farmer.primary.network.repositorys.metadata

import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 13/1/23.
 */

class MetaDataRepositoryImp @Inject constructor(private val api: FarmerApi) : MetaDataRepository {
    override suspend fun fetchMetaData(): NetworkResult<ResponseMetaInfo> {
       return api.getMeta()
    }
}
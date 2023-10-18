package com.farmer.primary.network.repositorys.metadata

import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 13/1/23.
 */
interface MetaDataRepository {
    suspend fun fetchMetaData(): NetworkResult<ResponseMetaInfo>
}
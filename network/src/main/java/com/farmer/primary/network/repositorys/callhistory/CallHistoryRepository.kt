package com.farmer.primary.network.repositorys.callhistory

import androidx.paging.PagingData
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import com.farmer.primary.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pritom Dutta on 7/9/23.
 */
interface CallHistoryRepository {
//    fun getCallHistory(userInfo: String, uuid: String): Flow<PagingData<ResponseCallHistory>>
    suspend fun getCallHistory(userInfo: String, uuid: String): NetworkResult<ResponseCallHistoryModel>
}
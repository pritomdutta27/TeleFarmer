package com.farmer.primary.network.repositorys.chat

import androidx.paging.PagingData
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody
import com.farmer.primary.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Pritom Dutta on 7/9/23.
 */
interface ChatRepository {
    suspend operator fun invoke(conversionId: String): NetworkResult<ResponsemessageBody>
}
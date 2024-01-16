package com.farmer.primary.network.repositorys.chat

import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 16/1/24.
 */
class ChatRepositoryImp @Inject constructor(
    private val api: FarmerApi
) : ChatRepository {
    override suspend fun invoke(conversionId: String) = api.getMess(conversionId)
}
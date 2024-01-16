package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody
import com.farmer.primary.network.utils.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Pritom Dutta on 16/1/24.
 */
interface LogUrlApi {

    @GET("chat-messge/{id}")
    suspend fun fetchMessages(@Path("id") id: String): NetworkResult<ResponsemessageBody>
}
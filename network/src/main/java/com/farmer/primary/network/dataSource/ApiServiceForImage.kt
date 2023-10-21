package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.CommonResponse
import com.farmer.primary.network.utils.NetworkResult
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 * Created by Pritom Dutta on 21/10/23.
 */
interface ApiServiceForImage {

    @Multipart
    @POST("labReportUploadApi")
    suspend fun imageUpload(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Part imageBody: MultipartBody.Part,
        @Query("id") user_id: String,
        @Query("folderName") folder: String,
        @Query("channel") channel: String
    ): NetworkResult<CommonResponse>
}
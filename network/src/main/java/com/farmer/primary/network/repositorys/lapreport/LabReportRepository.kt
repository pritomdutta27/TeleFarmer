package com.farmer.primary.network.repositorys.lapreport

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestLabReport
import bio.medico.patient.model.apiResponse.ResponseLabReport
import com.farmer.primary.network.utils.NetworkResult
import okhttp3.MultipartBody

/**
 * Created by Pritom Dutta on 21/10/23.
 */
interface LabReportRepository {
    suspend fun getLapReports(userInfo: String, uuid: String): NetworkResult<ResponseLabReport>

    suspend fun uploadImgFile(
        userInfo: String,
        uuid: String,
        imageBody: MultipartBody.Part,
        folder: MultipartBody.Part
    ): NetworkResult<CommonResponse>

    suspend fun patientLabReportFileURLUpload(
        userInfo: String,
        patientUpdate: Map<String,String>
    ): NetworkResult<CommonResponse>
}
package com.farmer.primary.network.repositorys.profile

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestPatientUpdate
import bio.medico.patient.model.apiResponse.RequestSignUp
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 8/9/23.
 */
interface ProfileRepository {
    suspend fun profileInfo(params: String): NetworkResult<ResponsePatientInfo>

    suspend fun updateProfile(userInfo: String, patientUpdate: RequestPatientUpdate): NetworkResult<CommonResponse>
    suspend fun requestReg(userInfo: String, patientUpdate: RequestSignUp, accessToken: String): NetworkResult<CommonResponse>
}
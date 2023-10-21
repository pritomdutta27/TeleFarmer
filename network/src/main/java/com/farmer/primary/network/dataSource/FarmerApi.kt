package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseLogin
import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.NetworkResult
import com.farmer.primary.network.utils.getBearerToken
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 13/1/23.
 */
class FarmerApi @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getMeta(): NetworkResult<ResponseMetaInfo> {
        return apiService.fetchMetaData()
    }

    suspend operator fun invoke(params: LoginParams): NetworkResult<ResponseLogin> {
        return apiService.loginUser(params)
    }

    suspend operator fun invoke(
        params: LoginOutParams,
        token: String
    ): NetworkResult<LoginResponse> {
        return apiService.userLogout(token.getBearerToken(), params)
    }

    suspend operator fun invoke(params: OtpParams): NetworkResult<OtpResponse> {
        return apiService.verifyOtp(params)
    }

    suspend operator fun invoke(token: String): NetworkResult<ResponseSingleDoctor> {
        return apiService.getAvailableDoctor(token.getBearerToken())
    }

    suspend operator fun invoke(
        token: String,
        phoneNumber: String
    ): NetworkResult<ProfileModel> {
        return apiService.fetchProfileData(token.getBearerToken(), phoneNumber)
    }

    suspend operator fun invoke(
        token: String,
        userInfo: String,
        requestStatusUpdate: RequestStatusUpdate
    ): NetworkResult<CommonResponse> {
        return apiService.doctorStatusUpdate(token, userInfo, requestStatusUpdate)
    }

    suspend operator fun invoke(
        token: String,
        userInfo: String,
        uuid: String,
        pageNumber: String,
        perpageCount: String
    ): NetworkResult<ResponseCallHistoryModel> {
        return apiService.getCallHistory(token.getBearerToken(), userInfo, uuid, pageNumber, perpageCount)
    }
}
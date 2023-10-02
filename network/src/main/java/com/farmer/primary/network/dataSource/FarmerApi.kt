package com.farmer.primary.network.dataSource

import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.doctor.DoctorAvailableResponse
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.NetworkResult
import com.farmer.primary.network.utils.getBearerToken
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 13/1/23.
 */
class FarmerApi @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getMeta(): NetworkResult<Map<String, MetaModel>> {
        return apiService.fetchMetaData()
    }

    suspend operator fun invoke(params: LoginParams): NetworkResult<LoginResponse> {
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

    suspend operator fun invoke(token: String): NetworkResult<Map<String, Doctor>> {
        return apiService.getAvailableDoctor(token.getBearerToken())
    }

    suspend operator fun invoke(
        token: String,
        phoneNumber: String
    ): NetworkResult<ProfileModel> {
        return apiService.fetchProfileData(token.getBearerToken(), phoneNumber)
    }
}
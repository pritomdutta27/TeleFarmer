package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Pritom Dutta on 13/1/23.
 */

interface ApiService {

    @GET("metaInfo")
    suspend fun fetchMetaData(): NetworkResult<ResponseMetaInfo>

    @POST("patient/passwordless-login")
    suspend fun loginUser(@Body params: LoginParams): NetworkResult<ResponseLogin>

    @POST("patient/passwordless-logout")
    suspend fun userLogout(
        @Header("Authorization") token: String,
        @Body params: LoginOutParams
    ): NetworkResult<LoginResponse>

    @POST("otp/otp-verification")
    suspend fun verifyOtp(@Body params: OtpParams): NetworkResult<OtpResponse>

    @GET("doctor/callDoctor")
    suspend fun getAvailableDoctor(@Header("Authorization") token: String): NetworkResult<ResponseSingleDoctor>

    @GET("patient/profile/{phoneNumber}")
    suspend fun fetchProfileData(
        @Header("Authorization") token: String,
        @Path("phoneNumber") phoneNumber: String
    ): NetworkResult<ProfileModel>

    @POST("doctor/statusUpdate")
    suspend fun doctorStatusUpdate(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Body requestStatusUpdate: RequestStatusUpdate
    ): NetworkResult<CommonResponse>
}
package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestLabReport
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseLabReport
import bio.medico.patient.model.apiResponse.ResponseLogin
import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.NetworkResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Pritom Dutta on 13/1/23.
 */

interface ApiService {

    @GET("staticData")
    suspend fun fetchHome(): NetworkResult<HomeResponse>

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
    ): NetworkResult<ResponsePatientInfo>

    @POST("doctor/statusUpdate")
    suspend fun doctorStatusUpdate(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Body requestStatusUpdate: RequestStatusUpdate
    ): NetworkResult<CommonResponse>

    @GET("callHistory/patient/{uuid}")
    suspend fun getCallHistory(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Path("uuid") uuid: String,
        @Query("pageNumber") pageNumber: String,
        @Query("perpageCount") perpageCount: String
    ): NetworkResult<ResponseCallHistoryModel>

    @GET("labReport/patient/{uuid}")
    suspend fun getLabReportList(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Path("uuid") uuid: String,
    ): NetworkResult<ResponseLabReport>

    //labReportUploadApi

    @POST("labReport")
    suspend fun patientPrescriptionFileURLUpload(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Body patientUpdate: Map<String,String>
    ): NetworkResult<CommonResponse>

    @POST("loan")
    suspend fun applyForLoan(
        @Header("Authorization") token: String,
        @Header("UserInfo") headerUserInfo: String,
        @Body patientInfo: Map<String,String>
    ): NetworkResult<CommonResponse>


}
package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestPatientUpdate
import bio.medico.patient.model.apiResponse.RequestSignUp
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseLabReport
import bio.medico.patient.model.apiResponse.ResponseLogin
import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.model.home.Static
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.model.weather.WeatherResponse
import com.farmer.primary.network.utils.NetworkResult
import com.farmer.primary.network.utils.getBearerToken
import okhttp3.MultipartBody
import retrofit2.http.Body
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 13/1/23.
 */
class FarmerApi @Inject constructor(
    private val apiService: ApiService,
    private val apiServiceForImage: ApiServiceForImage,
    private val weatherApi: WeatherApi,
    private val mLogUrlApi: LogUrlApi,
) {

    suspend fun fetchHome(): NetworkResult<HomeResponse> {
        return apiService.fetchHome()
    }

    suspend fun fetchNews(): NetworkResult<Static> {
        return apiService.fetchNews()
    }

    suspend fun fetchCategories(): NetworkResult<Static> {
        return apiService.fetchCategories()
    }

    suspend fun fetchTripsTricks(): NetworkResult<Static> {
        return apiService.fetchTripsTricks()
    }

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
    ): NetworkResult<ResponsePatientInfo> {
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
        return apiService.getCallHistory(
            token.getBearerToken(),
            userInfo,
            uuid,
            pageNumber,
            perpageCount
        )
    }

    suspend operator fun invoke(
        token: String,
        userInfo: String,
        uuid: String
    ): NetworkResult<ResponseLabReport> {
        return apiService.getLabReportList(token.getBearerToken(), userInfo, uuid)
    }

    suspend operator fun invoke(
        token: String,
        userInfo: String,
        imageBody: MultipartBody.Part,
        folder: MultipartBody.Part
    ): NetworkResult<CommonResponse> {
        return apiServiceForImage.imageUpload(
            token = token.getBearerToken(),
            headerUserInfo = userInfo,
            imageBody = imageBody,
            folder = folder
//            url = url
        )
    }

    suspend fun urlUpload(
        token: String,
        userInfo: String,
        patientUpdate: Map<String, String>
    ): NetworkResult<CommonResponse> {
        return apiService.patientPrescriptionFileURLUpload(
            token = token.getBearerToken(),
            headerUserInfo = userInfo,
            patientUpdate = patientUpdate
        )
    }

    suspend fun getWeatherReports(
    ): NetworkResult<WeatherResponse> {
        return weatherApi.getWeatherReports()
    }

    suspend fun applyLoan(
        token: String,
        userInfo: String,
        patientInfo: Map<String, String>
    ): NetworkResult<CommonResponse> {
        return apiService.applyForLoan(
            token = token.getBearerToken(),
            headerUserInfo = userInfo,
            patientInfo = patientInfo
        )
    }

    suspend fun updateProfile(
        token: String,
        userInfo: String,
        uuid: String,
        patientUpdate: RequestPatientUpdate
    ): NetworkResult<CommonResponse> {
        return apiService.updateProfile(
            token = token.getBearerToken(),
            headerUserInfo = userInfo,
            uuid = uuid,
            patientUpdate = patientUpdate
        )
    }

    suspend fun requestReg(
        token: String,
        userInfo: String,
        patientUpdate: RequestSignUp
    ): NetworkResult<CommonResponse> {
        return apiService.requestReg(
            token = token.getBearerToken(),
            headerUserInfo = userInfo,
            patientUpdate = patientUpdate
        )
    }

    ///////////////////////////////////////////////////////
    /// Chat
    //////////////////////////////////////////////////////
    suspend fun getMess(conversionId: String): NetworkResult<ResponsemessageBody>{
        return mLogUrlApi.fetchMessages(id = conversionId)
    }
}
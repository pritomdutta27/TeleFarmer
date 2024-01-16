package com.theroyalsoft.telefarmer.ui.view.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bio.medico.patient.model.apiResponse.RequestPatientUpdate
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.repositorys.lapreport.LabReportRepository
import com.farmer.primary.network.repositorys.login.LoginRepository
import com.farmer.primary.network.repositorys.profile.ProfileRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.theroyalsoft.telefarmer.model.loan.LoanDetailsResponseItem
import com.theroyalsoft.telefarmer.ui.view.bottomsheet.DivisionDataModel
import com.theroyalsoft.telefarmer.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 7/9/23. patient/passwordless-logout
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val mProfileRepository: ProfileRepository,
    private val pref: DataStoreRepository,
    private val mLabReportRepository: LabReportRepository
) : ViewModel() {

    private fun getPhone() =
        runBlocking { pref.getString(AppConstants.PREF_KEY_USER_PHONE_NUM) ?: "" }

    private fun setProfileInfo(data: ResponsePatientInfo?) = runBlocking {
//        LocalData.setUserProfile(data.toString())
        LocalData.setUserProfileAll(data)
        pref.putModel(AppConstants.PREF_KEY_USER_INFO, data)
    }

    private val logoutStateFlow by lazy {
        MutableSharedFlow<LoginResponse>()
    }

    val _logoutStateFlow: SharedFlow<LoginResponse> = logoutStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun logout(deviceID: String) {

        viewModelScope.launch {
            val params = LoginOutParams(getPhone(), deviceID)
            val response = repository.loginOut(params)
            response.onSuccess { res ->
                logoutStateFlow.emit(res)
            }.onError { code, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                errorFlow.emit("$error")
            }
        }
    }

    fun getDistricts(): List<DivisionDataModel> {
        return JsonUtils.getDistricts("bd_districts.json").sortedBy { it.bn_name }
    }


    private val imgUrlStateFlow by lazy {
        MutableSharedFlow<String>()
    }
    val _imgUrlStateFlow: SharedFlow<String> = imgUrlStateFlow
    fun uploadFile(imgPart: MultipartBody.Part, folderName: String, profileData: RequestPatientUpdate) {
        val params: MultipartBody.Part = MultipartBody.Part.createFormData("folderName", folderName)
        val headerUserInfo: String = UserDevices.getUserDevicesJson("profileUploadApi")

        viewModelScope.launch {
            mLabReportRepository.uploadImgFile(
                headerUserInfo,
                LocalData.getUserUuid(),
                imgPart, params
            ).onSuccess { res ->
                profileData.image = res.file
                updateProfile(profileData)
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }

    fun updateProfile(profileData: RequestPatientUpdate){
        val headerUserInfo: String = UserDevices.getUserDevicesJson("profileUploadApi")
        viewModelScope.launch {
            mProfileRepository.updateProfile(headerUserInfo, profileData)
                .onSuccess { res ->
                    imgUrlStateFlow.emit(profileData.image.toString())
                    fetchProfile()
                }.onError { _, message ->
                    //Log.e("setMetaData", "setMetaData: "+message)
                    errorFlow.emit("Message: $message")
                }.onException { error ->
                    // Log.e("setMetaData", "setMetaData: "+error)
                    errorFlow.emit("$error")
                }
        }
    }

    fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mProfileRepository.profileInfo(getPhone())
            response.onSuccess { res ->
                setProfileInfo(res)
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }
}
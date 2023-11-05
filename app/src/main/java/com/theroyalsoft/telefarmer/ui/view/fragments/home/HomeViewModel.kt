package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bio.medico.patient.common.AppKey
import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestLabReport
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseLabReport
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.model.weather.WeatherResponse
import com.farmer.primary.network.repositorys.callhistory.CallHistoryRepository
import com.farmer.primary.network.repositorys.home.HomeRepository
import com.farmer.primary.network.repositorys.lapreport.LabReportRepository
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.repositorys.weather.WeatherRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 6/9/23.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CallHistoryRepository,
    private val mLabReportRepository: LabReportRepository,
    private val homeRepository: HomeRepository,
    private val weatherRepository: WeatherRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    fun getMetaData() = runBlocking {
        pref.getModel(AppConstants.PREF_KEY_META_INFO, MetaModel::class.java)
    }

//    fun getCallHistory(): Flow<PagingData<ResponseCallHistory>> {
////        Timber.e("Call")
////        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
////        return repository.getCallHistory(headerUserInfo, LocalData.getUserUuid()).cachedIn(viewModelScope)
//    }

    private val homeStateFlow by lazy {
        MutableSharedFlow<HomeResponse>()
    }
    val _homeStateFlow: SharedFlow<HomeResponse> = homeStateFlow

    private val weatherRes by lazy {
        MutableSharedFlow<WeatherResponse>()
    }
    val _weatherRes: SharedFlow<WeatherResponse> = weatherRes

    private val historyStateFlow by lazy {
        MutableSharedFlow<ResponseCallHistoryModel>()
    }
    val _historyStateFlow: SharedFlow<ResponseCallHistoryModel> = historyStateFlow

    private val labStateFlow by lazy {
        MutableSharedFlow<ResponseLabReport>()
    }
    val _labStateFlow: SharedFlow<ResponseLabReport> = labStateFlow

    private val imgUrlStateFlow by lazy {
        MutableSharedFlow<CommonResponse>()
    }
    val _imgUrlStateFlow: SharedFlow<CommonResponse> = imgUrlStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow


    fun getHomeData() {
        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
        viewModelScope.launch(Dispatchers.IO) {
            val history =
                async { repository.getCallHistory(headerUserInfo, LocalData.getUserUuid()) }
            val report = async {
                mLabReportRepository.getLapReports(
                    headerUserInfo,
                    LocalData.getUserUuid()
                )
            }

            val weather = async {weatherRepository.getWeather()}

            val home = async { homeRepository.fetchHome() }

            val list = awaitAll(history, report, home, weather)

            list[0].onSuccess { res ->
                historyStateFlow.emit(res as ResponseCallHistoryModel)
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }

            list[1].onSuccess { res ->
                labStateFlow.emit(res as ResponseLabReport)
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }

            list[2].onSuccess { res ->
                homeStateFlow.emit(res as HomeResponse)
            }.onError { _, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }

            list[3].onSuccess { res ->
                weatherRes.emit(res as WeatherResponse)
            }.onError { _, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }

    fun getCallHistory() {
        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
        viewModelScope.launch {
            repository.getCallHistory(headerUserInfo, LocalData.getUserUuid())
                .onSuccess { res ->
                    historyStateFlow.emit(res)
                }.onError { _, message ->
                    //Log.e("setMetaData", "setMetaData: "+message)
                    errorFlow.emit("Message: $message")
                }.onException { error ->
                    // Log.e("setMetaData", "setMetaData: "+error)
                    errorFlow.emit("$error")
                }
        }
    }

    fun getLabReport() {
        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
        viewModelScope.launch {
            mLabReportRepository.getLapReports(
                headerUserInfo,
                LocalData.getUserUuid()
            )
                .onSuccess { res ->
                    labStateFlow.emit(res as ResponseLabReport)
                }.onError { _, message ->
                    //Log.e("setMetaData", "setMetaData: "+message)
                    errorFlow.emit("Message: $message")
                }.onException { error ->
                    // Log.e("setMetaData", "setMetaData: "+error)
                    errorFlow.emit("$error")
                }
        }
    }

    fun uploadFile(imgPart: MultipartBody.Part) {
        val params: MultipartBody.Part = MultipartBody.Part.createFormData("folderName", "labReport")
        val headerUserInfo: String = UserDevices.getUserDevicesJson("labReportUploadApi")

        viewModelScope.launch {
            mLabReportRepository.uploadImgFile(
                headerUserInfo,
                LocalData.getUserUuid(),
                imgPart, params
            ).onSuccess { res ->
                urlStore(res.file)
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }

    fun urlStore(url: String) {
        val headerUserInfo: String = UserDevices.getUserDevicesJson("labReport")
//        val labReport =   RequestLabReport(LocalData.getUserUuid(),url, AppKey.CHANNEL)
        val labReport = mutableMapOf<String,String>()
        labReport["patientUuid"] = LocalData.getUserUuid()
        labReport["fileUrl"] = url
        labReport["channel"] = AppKey.CHANNEL
        viewModelScope.launch {
            mLabReportRepository.patientLabReportFileURLUpload(
                headerUserInfo,
                labReport
            ).onSuccess { res ->
                imgUrlStateFlow.emit(res)
            }.onError { _, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }
}
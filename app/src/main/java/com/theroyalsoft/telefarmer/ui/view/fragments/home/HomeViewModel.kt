package com.theroyalsoft.telefarmer.ui.view.fragments.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseCallHistory
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.repositorys.callhistory.CallHistoryRepository
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 6/9/23.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CallHistoryRepository,
    private val pref: DataStoreRepository
): ViewModel() {

//    fun getCallHistory(): Flow<PagingData<ResponseCallHistory>> {
////        Timber.e("Call")
////        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
////        return repository.getCallHistory(headerUserInfo, LocalData.getUserUuid()).cachedIn(viewModelScope)
//    }

    private val historyStateFlow by lazy {
        MutableSharedFlow<ResponseCallHistoryModel>()
    }
    val _historyStateFlow: SharedFlow<ResponseCallHistoryModel> = historyStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow
    fun getCallHistory(){
        val headerUserInfo: String = UserDevices.getUserDevicesJson("callHistory/patient")
        viewModelScope.launch {
            repository.getCallHistory(headerUserInfo,LocalData.getUserUuid()).onSuccess { res ->
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
}
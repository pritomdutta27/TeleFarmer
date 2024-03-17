package com.theroyalsoft.telefarmer.ui.view.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.dataSource.local.LocalData
import bio.medico.patient.model.apiResponse.ResponseMetaInfo
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.repositorys.profile.ProfileRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.theroyalsoft.telefarmer.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 29/8/23.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MetaDataRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    init {
        fetchMetaData()
    }

    private fun setMetaData(metaData: ResponseMetaInfo) = runBlocking {
        metaData?.let {
            LocalData.saveData(it)
            pref.putModel(AppConstants.PREF_KEY_META_INFO, it)
            //pref.putModel(AppConstants.PREF_KEY_ACCESS_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyIjp7Il9pZCI6IjE3N2VjNGI1NDk4MTM2YWM2MjQzMDY1OGY2NWMxYTQ0IiwiX3JldiI6IjI5LTk5NjEyYjhmYTY3YzkyM2ZkNTVlOTdjODhhOThiMzg2IiwiYW5kcm9pZERldmljZUlkIjoibm8iLCJjaGFubmVsIjoiaW9zIiwidXVpZCI6IjhhMWVlMTY2LTMxMTUtNDJiZC05MzU4LTRmYTgyZmIwZWRmNCIsImlvc0RldmljZUlkIjoiNzg3MmVlMGMzNDlmZWYzNDBmODgwMTgzMDRkYThiNWRjMjBlZjA4YmU2OGRkYmQ3ZDAxMzhhNzgyNGQwMjRmNCIsIm1hbnVhbFN0YXR1cyI6Im9mZmxpbmUiLCJuYW1lIjoiS2FtcnVsIEhhc2FuIiwicGhvbmVOdW1iZXIiOiI4ODAxNzQ1Mjc3MTQ1IiwieG1wcElkIjoiOGExZWUxNjYtMzExNS00MmJkLTkzNTgtNGZhODJmYjBlZGY0IiwieG1wcFN0YXR1cyI6Im9mZmxpbmUiLCJjcmVhdGVBdCI6IjIwMjItMDUtMTJUMTE6MjA6NDIrMDA6MDAiLCJ1cGRhdGVkQXQiOiIyMDIyLTA1LTEyVDExOjIwOjQyKzAwOjAwIiwiZG9iIjoiTWF5IDEyLCAxOTk1IiwiZ2VuZGVyIjoiTWFsZSIsIndlaWdodCI6IjYwIEtHIiwiaGVpZ2h0IjoiNSBmdCA0IGluIiwibG9jYXRpb24iOiIwMTc0NTI3NzE0NSIsImltYWdlIjoicGF0aWVudC84M0RBMkYyQi1BQ0FFLTRBRkUtOTNBMi04RkQxQkE2MUEzOTQucG5nIn0sImlhdCI6MTY1ODczOTM4MCwiZXhwIjoxNjU4ODI1NzgwfQ.Nx01AOvvTJkYp9uPvJRwOcooRZwf7Xk-5SD0erUvwQ8")
        }

    }



    private fun getToken() = runBlocking{
        pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN)
    }

    private fun getPhone() = runBlocking {
        pref.getString(AppConstants.PREF_KEY_USER_PHONE_NUM) ?: ""
    }

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    private fun fetchMetaData() {

        viewModelScope.launch {
//            LocalData.setToken(getToken())
//            val metaData = JsonUtils.getMetaData("meta_data.json")
//            LocalData.saveData(metaData)
//            val profile = JsonUtils.getProfile("profile_info.json")
//            LocalData.setUserProfileAll(profile)

//            Timber.e("metaData ${LocalData.getToken()}")
            delay(100)
            val response = repository.fetchMetaData()
            response.onSuccess { res ->
                setMetaData(res)
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


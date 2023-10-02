package com.theroyalsoft.telefarmer.ui.view.activity.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.model.metadata.MetaDataResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.repositorys.metadata.MetaDataRepository
import com.farmer.primary.network.repositorys.profile.ProfileRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.theroyalsoft.telefarmer.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 29/8/23.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MetaDataRepository,
    private val repositoryProfile: ProfileRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    private fun setMetaData(metaData: MetaModel?) = runBlocking {
        pref.putModel(AppConstants.PREF_KEY_META_INFO, metaData)
    }

    private fun setProfileInfo(data: ProfileModel?) = runBlocking {
        LocalData.setUserProfile(data.toString())
        pref.putModel(AppConstants.PREF_KEY_USER_INFO, data)
    }

    private fun getPhone() = runBlocking {
        pref.getString(AppConstants.PREF_KEY_USER_PHONE_NUM) ?: ""
    }

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun fetchMetaData() {
        viewModelScope.launch {
            val response = repository.fetchMetaData()
            response.onSuccess { res ->
                setMetaData(res["meta"])
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
        viewModelScope.launch {
            val response = repositoryProfile.profileInfo(getPhone())
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


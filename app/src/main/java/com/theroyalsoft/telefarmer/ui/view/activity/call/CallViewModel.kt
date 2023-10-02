package com.theroyalsoft.telefarmer.ui.view.activity.call

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.doctor.DoctorAvailableResponse
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.repositorys.doctor.AvailableDoctorRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 6/9/23.
 */
@HiltViewModel
class CallViewModel @Inject constructor(
    private val pref: DataStoreRepository, private val repository: AvailableDoctorRepository
) : ViewModel() {

    fun getMetaData() = runBlocking {
        pref.getModel(AppConstants.PREF_KEY_META_INFO, MetaModel::class.java)
    }

    fun getProfile() = runBlocking {
        pref.getModel(AppConstants.PREF_KEY_USER_INFO, ProfileModel::class.java)
    }

    // Api
    private val doctorStateFlow by lazy {
        MutableSharedFlow<Doctor>()
    }

    val _doctorStateFlow: SharedFlow<Doctor> = doctorStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun fetchAvailableDoctor() {
        viewModelScope.launch {
            val response = repository.getAvailableDoctors()
            response.onSuccess { res ->
                res["doctor"]?.let { doctorStateFlow.emit(it) }
            }.onError { code, message ->
                errorFlow.emit(if (code == 422) "422" else "Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }
}
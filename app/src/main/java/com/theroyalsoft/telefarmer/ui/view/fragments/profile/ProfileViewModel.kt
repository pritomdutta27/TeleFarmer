package com.theroyalsoft.telefarmer.ui.view.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.repositorys.login.LoginRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 7/9/23. patient/passwordless-logout
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    private fun getPhone() =
        runBlocking { pref.getString(AppConstants.PREF_KEY_USER_PHONE_NUM) ?: "" }

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
}
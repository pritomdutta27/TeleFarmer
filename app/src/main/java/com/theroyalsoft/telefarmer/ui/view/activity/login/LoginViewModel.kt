package com.theroyalsoft.telefarmer.ui.view.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bio.medico.patient.model.apiResponse.ResponseLogin
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.login.LoginParams
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
 * Created by Pritom Dutta on 28/8/23.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val pref: DataStoreRepository,
) : ViewModel() {

    private val loginStateFlow by lazy {
        MutableSharedFlow<ResponseLogin>()
    }

    val _loginStateFlow: SharedFlow<ResponseLogin> = loginStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun setLogin(accessToken: String, phone: String) = runBlocking {
        pref.userLoginMode()
        LocalData.setToken(accessToken)
        pref.putString(AppConstants.PREF_KEY_ACCESS_TOKEN, accessToken)
        pref.putString(AppConstants.PREF_KEY_USER_PHONE_NUM, phone)
    }

    fun submitLogin(params: LoginParams) {
        viewModelScope.launch {
            val response = repository.login(params)
            response.onSuccess { res ->
                loginStateFlow.emit(res)
            }.onError { code, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                errorFlow.emit("$error")
            }
        }
    }
}
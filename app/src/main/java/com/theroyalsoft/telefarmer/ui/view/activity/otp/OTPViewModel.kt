package com.theroyalsoft.telefarmer.ui.view.activity.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.otp.OtpParams
import com.farmer.primary.network.model.otp.OtpResponse
import com.farmer.primary.network.repositorys.otp.OtpRepository
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
class OTPViewModel @Inject constructor(
    private val repository: OtpRepository,
    private val pref: DataStoreRepository
) : ViewModel() {


    private val otpStateFlow by lazy {
        MutableSharedFlow<OtpResponse>()
    }


    val _otpStateFlow: SharedFlow<OtpResponse> = otpStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    private fun setLogin(data: OtpResponse, phone: String) = runBlocking {
        pref.userLoginMode()
        LocalData.setToken(data.accessToken)
        pref.putString(AppConstants.PREF_KEY_ACCESS_TOKEN, data.accessToken)
        pref.putString(AppConstants.PREF_KEY_USER_PHONE_NUM, phone)
    }

    fun verify(params: OtpParams) {
        viewModelScope.launch {
            val response = repository.verifyOtp(params)
            response.onSuccess { res ->
                setLogin(res, params.phoneNumber)
                otpStateFlow.emit(res)
            }.onError { code, message ->
                errorFlow.emit("Message: $message")
            }.onException { error ->
                errorFlow.emit("$error")
            }
        }
    }

}
package bio.medico.patient.callingWebrtc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.metadata.MetaModel
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.repositorys.doctor.AvailableDoctorRepository
import com.farmer.primary.network.repositorys.doctor.UpdateDoctorStatus
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
 * Created by Pritom Dutta on 6/9/23.
 */
@HiltViewModel
class CallActivityViewModel @Inject constructor(
    private val pref: DataStoreRepository,
    private val repository: AvailableDoctorRepository,
    private val repositoryUpdate: UpdateDoctorStatus
) : ViewModel() {

    fun getMetaData() = runBlocking {
        pref.getModel(AppConstants.PREF_KEY_META_INFO, MetaModel::class.java)
    }

    fun getProfile() = runBlocking {
        pref.getModel(AppConstants.PREF_KEY_USER_INFO, ProfileModel::class.java)
    }

    // Api
    private val doctorStateFlow by lazy {
        MutableSharedFlow<ResponseSingleDoctor>()
    }
    val _doctorStateFlow: SharedFlow<ResponseSingleDoctor> = doctorStateFlow

    private val doctorStatus by lazy {
        MutableSharedFlow<CommonResponse>()
    }
    val _doctorStatus: SharedFlow<CommonResponse> = doctorStatus

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    fun fetchAvailableDoctor() {
        viewModelScope.launch {
            val response = repository.getAvailableDoctors()
            response.onSuccess { res ->
                doctorStateFlow.emit(res)
            }.onError { code, message ->
                errorFlow.emit(if (code == 422) "422" else "Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                errorFlow.emit("$error")
            }
        }
    }

    fun updateDoctorStatus(doctorUuid: String, status: String) {
        viewModelScope.launch {
            val headerUserInfo: String = UserDevices.getUserDevicesJson("doctor/statusUpdate")
            val requestSingIn = RequestStatusUpdate(doctorUuid, status)
            val response = repositoryUpdate.updateDoctorStatus(headerUserInfo, requestSingIn)
            response.onSuccess { res ->
                doctorStatus.emit(res)
            }.onError { code, message ->
                errorFlow.emit(if (code == 422) "422" else "Message: $message")
            }.onException { error ->
                errorFlow.emit("$error")
            }
        }
    }
}
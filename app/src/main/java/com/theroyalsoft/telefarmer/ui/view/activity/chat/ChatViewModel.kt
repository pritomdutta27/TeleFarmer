package com.theroyalsoft.telefarmer.ui.view.activity.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.MessageModel
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.repositorys.chat.ChatRepository
import com.farmer.primary.network.repositorys.lapreport.LabReportRepository
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 16/1/24.
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val mLabReportRepository: LabReportRepository,
) : ViewModel() {

    init {
        getMessages()
    }

    private val chatStateFlow by lazy {
        MutableSharedFlow<ResponsemessageBody>()
    }
    val _chatStateFlow: SharedFlow<ResponsemessageBody> = chatStateFlow

    private val imgUrlStateFlow by lazy {
        MutableSharedFlow<String>()
    }
    val _imgUrlStateFlow: SharedFlow<String> = imgUrlStateFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    private fun getMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            val conversionId = MessageModel.getConversationID()
            repository.invoke(conversionId)
                .onSuccess { chatStateFlow.emit(it) }
                .onException { errorFlow.emit(it.message.toString()) }
                .onError { code, message -> errorFlow.emit(message.toString()) }
        }
    }

    fun uploadFile(imgPart: MultipartBody.Part, folderName: String) {
        val params: MultipartBody.Part = MultipartBody.Part.createFormData("folderName", folderName)
        val headerUserInfo: String = UserDevices.getUserDevicesJson("chatUploadApi")

        viewModelScope.launch {
            mLabReportRepository.uploadImgFile(
                headerUserInfo,
                LocalData.getUserUuid(),
                imgPart, params
            ).onSuccess { res ->
                imgUrlStateFlow.emit(res.file)
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
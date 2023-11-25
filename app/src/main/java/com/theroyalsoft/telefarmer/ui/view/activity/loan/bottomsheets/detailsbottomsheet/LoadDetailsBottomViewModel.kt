package com.theroyalsoft.telefarmer.ui.view.activity.loan.bottomsheets.detailsbottomsheet

import android.app.Dialog
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.repositorys.lapreport.LabReportRepository
import com.farmer.primary.network.repositorys.loan.LoanRepository
import com.farmer.primary.network.utils.onError
import com.farmer.primary.network.utils.onException
import com.farmer.primary.network.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 25/11/23.
 */
@HiltViewModel
class LoadDetailsBottomViewModel @Inject constructor(
    private val mLabReportRepository: LabReportRepository,
    private val mLoanRepository: LoanRepository,
    private val pref: DataStoreRepository
) : ViewModel() {

    private val successFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val _successFlow: SharedFlow<Boolean> = successFlow

    private val errorFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val _errorFlow: SharedFlow<String> = errorFlow

    private var frontUrl = ""
    private var backUrl = ""
    fun uploadFile(
        imgPart: MultipartBody.Part,
        folderName: String,
        isFront: Boolean,
        loadingDialog: Dialog?,
        frontTxt: TextView,
        backTxt: TextView
    ) {
        val params: MultipartBody.Part = MultipartBody.Part.createFormData("folderName", folderName)
        val headerUserInfo: String = UserDevices.getUserDevicesJson("labReportUploadApi")

        viewModelScope.launch {
            mLabReportRepository.uploadImgFile(
                headerUserInfo,
                LocalData.getUserUuid(),
                imgPart, params
            ).onSuccess { res ->
                withContext(Dispatchers.Main) {
                    loadingDialog?.dismiss()
                    if (isFront) {
                        frontUrl = res.file
                        frontTxt.text = frontUrl.replace("nidCard/", "")
                    } else {
                        backUrl = res.file
                        backTxt.text = backUrl.replace("nidCard/", "")
                    }
                }
            }.onError { _, message ->
                //Log.e("setMetaData", "setMetaData: "+message)
                loadingDialog?.dismiss()
                errorFlow.emit("Message: $message")
            }.onException { error ->
                // Log.e("setMetaData", "setMetaData: "+error)
                loadingDialog?.dismiss()
                errorFlow.emit("$error")
            }
        }
    }

    fun applyLoan(cropName: String, landAmount: String, totalLoan: String): Boolean {
        if (frontUrl.isEmpty() || backUrl.isEmpty())
            return true

        val headerUserInfo: String = UserDevices.getUserDevicesJson("loan")
        val params = mutableMapOf<String,String>()

        params["url_nid_front"] = frontUrl
        params["url_nid_back"] = backUrl
        params["crop_type_name"] = cropName
        params["amount_of_land"] = landAmount
        params["total_amount_of_loan"] = totalLoan

        viewModelScope.launch {
            mLoanRepository.applyLoan(headerUserInfo, params)
                .onSuccess { res ->
                    successFlow.emit(res.isSuccess)
                }.onError { _, message ->
                    //Log.e("setMetaData", "setMetaData: "+message)
                    errorFlow.emit("Message: $message")
                }.onException { error ->
                    // Log.e("setMetaData", "setMetaData: "+error)
                    errorFlow.emit("$error")
                }
        }
        return false
    }
}
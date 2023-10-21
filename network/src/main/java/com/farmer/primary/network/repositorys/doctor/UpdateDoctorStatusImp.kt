package com.farmer.primary.network.repositorys.doctor

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 18/10/23.
 */
class UpdateDoctorStatusImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : UpdateDoctorStatus {
    override suspend fun updateDoctorStatus(
        userInfo: String,
        requestStatusUpdate: RequestStatusUpdate
    ): NetworkResult<CommonResponse> {
        return api.invoke(
            token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
            userInfo,
            requestStatusUpdate
        )
    }

}
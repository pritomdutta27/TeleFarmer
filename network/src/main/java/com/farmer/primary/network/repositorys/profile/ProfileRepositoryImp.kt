package com.farmer.primary.network.repositorys.profile

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestPatientUpdate
import bio.medico.patient.model.apiResponse.RequestSignUp
import bio.medico.patient.model.apiResponse.ResponsePatientInfo
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 8/9/23.
 */

class ProfileRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : ProfileRepository {

    override suspend fun profileInfo(params: String): NetworkResult<ResponsePatientInfo> {
        return api.invoke(pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "", params)
    }

    override suspend fun updateProfile(
        userInfo: String,
        patientUpdate: RequestPatientUpdate
    ): NetworkResult<CommonResponse> {
        return api.updateProfile(
            token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
            userInfo = userInfo,
            uuid = LocalData.getUserUuid(),
            patientUpdate = patientUpdate
        )
    }

    override suspend fun requestReg(
        userInfo: String,
        patientUpdate: RequestSignUp
    ): NetworkResult<CommonResponse> {
        return api.requestReg(
            token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
            userInfo = userInfo,
            patientUpdate = patientUpdate
        )
    }
}
package com.farmer.primary.network.repositorys.loan

import bio.medico.patient.model.apiResponse.CommonResponse
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.repositorys.home.HomeRepository
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 25/11/23.
 */
class LoanRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : LoanRepository {
    override suspend fun applyLoan(
        userInfo: String,
        patientInfo: Map<String, String>
    ): NetworkResult<CommonResponse> {
        return api.applyLoan(
            token = pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "",
            userInfo = userInfo,
            patientInfo = patientInfo
        )
    }
}
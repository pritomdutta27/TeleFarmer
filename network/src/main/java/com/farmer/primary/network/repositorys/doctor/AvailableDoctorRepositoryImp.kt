package com.farmer.primary.network.repositorys.doctor

import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.doctor.DoctorAvailableResponse
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 7/9/23.
 */
class AvailableDoctorRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : AvailableDoctorRepository {
    override suspend fun getAvailableDoctors(): NetworkResult<ResponseSingleDoctor> {
        return api.invoke(pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "")
    }
}
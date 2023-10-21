package com.farmer.primary.network.repositorys.doctor

import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.RequestStatusUpdate
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 18/10/23.
 */
interface UpdateDoctorStatus {
    suspend fun updateDoctorStatus(userInfo: String, requestStatusUpdate: RequestStatusUpdate): NetworkResult<CommonResponse>
}
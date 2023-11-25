package com.farmer.primary.network.repositorys.loan

import bio.medico.patient.model.apiResponse.CommonResponse
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 25/11/23.
 */
interface LoanRepository {
    suspend fun applyLoan( userInfo: String, patientInfo: Map<String,String>): NetworkResult<CommonResponse>
}
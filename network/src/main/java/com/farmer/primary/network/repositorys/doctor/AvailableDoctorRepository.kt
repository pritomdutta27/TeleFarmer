package com.farmer.primary.network.repositorys.doctor

import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.doctor.DoctorAvailableResponse
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 7/9/23.
 */
interface AvailableDoctorRepository {
    suspend fun getAvailableDoctors(): NetworkResult<Map<String, Doctor>>
}
package com.farmer.primary.network.repositorys.login

import bio.medico.patient.model.apiResponse.ResponseLogin
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.utils.NetworkResult


/**
 * Created by Pritom Dutta on 28/8/23.
 */
interface LoginRepository {
    suspend fun login(params: LoginParams): NetworkResult<ResponseLogin>

    suspend fun loginOut(params: LoginOutParams): NetworkResult<LoginResponse>
}
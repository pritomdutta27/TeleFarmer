package com.farmer.primary.network.repositorys.login

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.login.LoginOutParams
import com.farmer.primary.network.model.login.LoginParams
import com.farmer.primary.network.model.login.LoginResponse
import com.farmer.primary.network.utils.AppConstants
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 28/8/23.
 */

class LoginRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : LoginRepository {

    override suspend fun login(params: LoginParams) = api.invoke(params)

    override suspend fun loginOut(params: LoginOutParams): NetworkResult<LoginResponse> {
        return api.invoke(params, pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "")
    }
}
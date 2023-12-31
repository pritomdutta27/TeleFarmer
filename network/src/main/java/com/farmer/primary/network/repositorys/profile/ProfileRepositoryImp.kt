package com.farmer.primary.network.repositorys.profile

import com.farmer.primary.network.dataSource.FarmerApi
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
    override suspend fun profileInfo(params: String): NetworkResult<ProfileModel> {
        return api.invoke(pref.getString(AppConstants.PREF_KEY_ACCESS_TOKEN) ?: "", params)
    }
}
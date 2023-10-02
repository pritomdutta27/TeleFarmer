package com.farmer.primary.network.repositorys.profile

import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 8/9/23.
 */
interface ProfileRepository {
    suspend fun profileInfo(params: String): NetworkResult<ProfileModel>
}
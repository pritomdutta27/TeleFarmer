package com.farmer.primary.network.repositorys.home

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 21/10/23.
 */
interface HomeRepository {
    suspend fun fetchHome(): NetworkResult<HomeResponse>
}
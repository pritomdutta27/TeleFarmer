package com.farmer.primary.network.repositorys.home

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.model.home.NewsModel
import com.farmer.primary.network.model.home.Static
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 21/10/23.
 */
class HomeRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
): HomeRepository{
    override suspend fun fetchHome() = api.fetchHome()
    override suspend fun fetchNews() = api.fetchNews()
    override suspend fun fetchCategories() = api.fetchCategories()
    override suspend fun fetchTripsTricks() = api.fetchTripsTricks()
}

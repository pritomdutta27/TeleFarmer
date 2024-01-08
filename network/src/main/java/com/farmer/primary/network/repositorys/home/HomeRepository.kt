package com.farmer.primary.network.repositorys.home

import com.farmer.primary.network.model.home.HomeResponse
import com.farmer.primary.network.model.home.Static
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 21/10/23.
 */
interface HomeRepository {
    suspend fun fetchHome(): NetworkResult<HomeResponse>
    suspend fun fetchNews(): NetworkResult<Static>
    suspend fun fetchCategories(): NetworkResult<Static>
    suspend fun fetchTripsTricks(): NetworkResult<Static>
}
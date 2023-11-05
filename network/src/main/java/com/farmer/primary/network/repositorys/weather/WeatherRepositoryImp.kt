package com.farmer.primary.network.repositorys.weather

import com.farmer.primary.network.dataSource.FarmerApi
import com.farmer.primary.network.model.weather.WeatherResponse
import com.farmer.primary.network.utils.NetworkResult
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import javax.inject.Inject

/**
 * Created by Pritom Dutta on 5/11/23.
 */
class WeatherRepositoryImp @Inject constructor(
    private val api: FarmerApi,
    private val pref: DataStoreRepository
) : WeatherRepository {
    override suspend fun getWeather(): NetworkResult<WeatherResponse> = api.getWeatherReports()
}
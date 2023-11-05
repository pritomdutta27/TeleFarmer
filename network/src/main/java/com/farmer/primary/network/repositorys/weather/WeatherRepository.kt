package com.farmer.primary.network.repositorys.weather

import com.farmer.primary.network.model.weather.WeatherResponse
import com.farmer.primary.network.utils.NetworkResult

/**
 * Created by Pritom Dutta on 5/11/23.
 */
interface WeatherRepository {
    suspend fun getWeather(): NetworkResult<WeatherResponse>
}
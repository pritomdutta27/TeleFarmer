package com.farmer.primary.network.dataSource

import com.farmer.primary.network.model.weather.WeatherResponse
import com.farmer.primary.network.utils.NetworkResult
import retrofit2.http.GET

/**
 * Created by Pritom Dutta on 5/11/23.
 */
interface WeatherApi {

    @GET("forecast.json?key=699089e17bc64955b9f72426230511&q=Dhaka&days=7&aqi=no&alerts=no")
    suspend fun getWeatherReports(): NetworkResult<WeatherResponse>
}
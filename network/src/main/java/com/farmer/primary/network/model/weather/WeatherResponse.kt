package com.farmer.primary.network.model.weather

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
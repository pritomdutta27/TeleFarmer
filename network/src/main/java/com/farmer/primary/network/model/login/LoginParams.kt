package com.farmer.primary.network.model.login

data class LoginParams(
    val channel: String,
    val deviceId: String,
    val phoneNumber: String,
    val type: String,
    val userType: String
)

data class LoginOutParams(
    val phoneNumber: String,
    val deviceId: String
)
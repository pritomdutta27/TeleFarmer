package com.farmer.primary.network.model.login

data class LoginResponse(
    val accessToken: String,
    val isProfile: Boolean,
    val isSuccess: Boolean,
    val isVerified: Boolean,
    val message: String,
    val phoneNumber: String
)
package com.farmer.primary.network.model.otp

data class OtpResponse(
    val instructionUrl: String,
    val isSuccess: Boolean,
    val message: String,
    val phoneNumber: String,
    val accessToken: String
)
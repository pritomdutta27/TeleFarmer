package com.farmer.primary.network.model.otp

data class OtpParams(
    val channel: String,
    val deviceId: String,
    val deviceName: String,
    val isTrusted: Boolean,
    val otp: String,
    val phoneNumber: String
)
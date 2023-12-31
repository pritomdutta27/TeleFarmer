package com.farmer.primary.network.model.doctor

data class Doctor(
    val _id: Any,
    val _rev: Any,
    val accessToken: Any,
    val address: Any,
    val bmdcNumber: String,
    val certificateFile: Any,
    val channel: String,
    val createdAt: String,
    val degree: List<Any>,
    val dob: String,
    val email: Any,
    val gender: Any,
    val id: Int,
    val image: String,
    val isApproved: Boolean,
    val isPushCall: Boolean,
    val manualStatus: String,
    val manualStatusId: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val specialty: List<Any>,
    val updatedAt: String,
    val uuid: String,
    val workPlace: Any,
    val xmppId: String,
    val xmppStatus: String
)
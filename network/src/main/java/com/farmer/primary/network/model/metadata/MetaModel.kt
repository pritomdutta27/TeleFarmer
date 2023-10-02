package com.farmer.primary.network.model.metadata

data class MetaModel(
    val apiBaseUrl: String?,
    val apiVersion: Int?,
    val appVersion: Int?,
    val baseUrl: String?,
    val chatUrl: String?,
    val description: String?,
    val doctorBaseUrl: String?,
    val imageUploadBaseUrl: String?,
    val imgBaseUrl: String?,
    val instructionsLink: String?,
    val logWriteInfo: LogWriteInfo?,
    val medicineDeliveryCharge: Int?,
    val password: String?,
    val playstoreLink: String?,
    val redirectAfterbilling: String?,
    val socket: Boolean?,
    val socketBaseUrl: String?,
    val stunServer: List<String>?,
    val title: String?,
    val token: String?,
    val turnAuth: TurnAuth?,
    val turnServer: List<String>?,
    val user: String?,
    val xmpp: Boolean?,
    val xmppInfo: XmppInfo?
)
package com.farmer.primary.network.model.sockets

/**
 * Created by Pritom Dutta on 9/9/23.
 */

data class SocketPassData(
    val uuid: String,
    val socketId: String,
    val userName: String,
    val userType: String,
    val userMobile: String,
    val dateAndTime: String
)

data class SocketPreOffer(
    val fromId: String,
    val toId: String,
    val preOfferAnswer: String
)

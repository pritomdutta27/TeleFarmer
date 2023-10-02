package com.farmer.primary.network.model.metadata

data class XmppInfo(
    val apiBaseUrl: String?,
    val bosPort: Int?,
    val c2sPort: Int?,
    val wsPort: Int?,
    val xmppDomain: String?
)
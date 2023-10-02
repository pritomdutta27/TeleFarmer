package com.theroyalsoft.telefarmer.sockethelper

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

/**
 * Created by Pritom Dutta on 9/9/23.
 */
class SimpleSdpObserver : SdpObserver {
    override fun onCreateSuccess(sessionDescription: SessionDescription?) {}
    override fun onSetSuccess() {}
    override fun onCreateFailure(s: String?) {}
    override fun onSetFailure(s: String?) {}
}

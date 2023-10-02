package com.theroyalsoft.telefarmer.model;

import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class SimpleSdpObserver implements SdpObserver {
    @Override
    public void onCreateSuccess(SessionDescription sessionDescription) {
        Timber.i("SdpObserver: onCreateSuccess !");
    }

    @Override
    public void onSetSuccess() {
        Timber.i("SdpObserver: onSetSuccess");
    }

    @Override
    public void onCreateFailure(String msg) {
        Timber.e("SdpObserver onCreateFailure: " + msg);
    }

    @Override
    public void onSetFailure(String msg) {
        Timber.e("SdpObserver onSetFailure: " + msg);
    }
}
package com.theroyalsoft.telefarmer.model;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class ProxyVideoSink implements VideoSink {
    private VideoSink mTarget;

    @Override
    synchronized public void onFrame(VideoFrame frame) {
        if (mTarget == null) {
            Timber.d("Dropping frame in proxy because target is null.");
            return;
        }
        mTarget.onFrame(frame);
    }

    public   synchronized void setTarget(VideoSink target) {
        this.mTarget = target;
    }
}
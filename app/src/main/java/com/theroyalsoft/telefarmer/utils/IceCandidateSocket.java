package com.theroyalsoft.telefarmer.utils;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class IceCandidateSocket {
    private String sdp;
    private int sdpMLineIndex;
    private String sdpMid;//sdpMid


    public IceCandidateSocket(String sdp, int sdpMLineIndex, String sdpMid) {
        this.sdp = sdp;
        this.sdpMLineIndex = sdpMLineIndex;
        this.sdpMid = sdpMid;

    }

    public String getSDP() {
        return NullRemoveUtil.getNotNull(sdp);
    }

    public int getSDPMLineIndex() {
        return sdpMLineIndex;
    }

    public String getSDPMid() {
        return NullRemoveUtil.getNotNull(sdpMid);
    }

}

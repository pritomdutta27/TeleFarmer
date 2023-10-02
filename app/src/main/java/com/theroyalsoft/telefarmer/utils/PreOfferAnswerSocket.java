package com.theroyalsoft.telefarmer.utils;

import com.theroyalsoft.telefarmer.helper.AppKey;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class PreOfferAnswerSocket {
    private String preOfferAnswer;
    private String fromId;
    private String toId;
    private String toDeviceId;
    private String fromDeviceId;
    private String channel;


    public PreOfferAnswerSocket(String preOfferAnswer, String fromId, String toId) {
        this.preOfferAnswer = preOfferAnswer;
        this.fromId = fromId;
        this.toId = toId;
        this.fromDeviceId = DeviceIDUtil.getDeviceID();
        this.toDeviceId = SocketKey.getReceiverDeviceId(); //Receiver DevicesId
        this.channel = AppKey.CHANNEL;
    }

    public String getFromDeviceId() {
        return NullRemoveUtil.getNotNull(fromDeviceId);
    }

    public String getPreOfferAnswer() {
        return NullRemoveUtil.getNotNull(preOfferAnswer);
    }
}

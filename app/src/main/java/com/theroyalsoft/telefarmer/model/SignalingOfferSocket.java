package com.theroyalsoft.telefarmer.model;

import com.theroyalsoft.telefarmer.helper.AppKey;
import com.theroyalsoft.telefarmer.utils.DeviceIDUtil;
import com.theroyalsoft.telefarmer.utils.SocketKey;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class SignalingOfferSocket {

    private String type;
    private String offer;
    private String answer;
    private String candidate;
    private String fromId;
    private String toId;
    private String toDeviceId;
    private String fromDeviceId;
    private String channel;


    public SignalingOfferSocket(String type, String offer, String answer, String candidate, String fromId, String toId) {
        this.type = type;
        this.offer = offer;
        this.answer = answer;
        this.candidate = candidate;
        this.fromId = fromId;
        this.toId = toId;

        this.fromDeviceId = DeviceIDUtil.getDeviceID();
        this.toDeviceId = SocketKey.getReceiverDeviceId(); //Receiver DevicesId
        this.channel = AppKey.CHANNEL;
    }

    public String getOffer() {
        return offer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCandidate() {
        return candidate;
    }

    public String getType() {
        return type;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}

package com.theroyalsoft.telefarmer.model;

import com.theroyalsoft.telefarmer.helper.AppKey;
import com.theroyalsoft.telefarmer.utils.DeviceIDUtil;
import com.theroyalsoft.telefarmer.utils.SocketKey;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class PreOfferSocket {
    private String patientName;
    private String patientImage;
    private String dateAndTime;
    private String callType;
    private String fromId;
    private String toId;
    private String toDeviceId;
    private String fromDeviceId;
    private String channel;

    public PreOfferSocket(String patientName, String patientImage, String dateAndTime, String callType, String fromId, String toId) {
        this.patientName = patientName;
        this.patientImage = patientImage;
        this.dateAndTime = dateAndTime;
        this.callType = callType;
        this.fromId = fromId;
        this.toId = toId;
        this.fromDeviceId = DeviceIDUtil.getDeviceID();
        this.toDeviceId = SocketKey.getReceiverDeviceId(); //Receiver DevicesId
        this.channel = AppKey.CHANNEL;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}

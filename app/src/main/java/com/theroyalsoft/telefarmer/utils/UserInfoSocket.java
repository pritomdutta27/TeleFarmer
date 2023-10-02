package com.theroyalsoft.telefarmer.utils;

import com.theroyalsoft.telefarmer.helper.AppKey;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class UserInfoSocket {
    private String uuid;
    private String socketId;
    private String userName;
    private String userMobile;
    private String dateAndTime;
    private String userType;
    private String deviceId;
    private String channel;

    public UserInfoSocket(String uuid, String socketId, String userName, String userMobile, String dateAndTime, String userType) {
        this.uuid = uuid;
        this.socketId = socketId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.dateAndTime = dateAndTime;
        this.userType = userType;
        this.deviceId = DeviceIDUtil.getDeviceID();
        this.channel = AppKey.CHANNEL;
    }
}

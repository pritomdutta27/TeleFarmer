package com.farmer.primary.network.dataSource.local;

import com.skh.hkhr.util.JsonUtil;

import bio.medico.patient.common.AppKey;
import bio.medico.patient.common.DeviceIDUtil;

/**
 * Created by Pritom Dutta on 18/10/23.
 */
public class UserDevices {
    private static UserDevices userDevices;

    private String deviceId;
    private String uuid;
    private String phoneNumber;
    private String apiEndPoint;
    private String appVersion;
    private String deviceName;
    private String channel;

    public UserDevices() {
        this.deviceId = DeviceIDUtil.getDeviceID();
        this.uuid = LocalData.getUserUuid();
        this.phoneNumber = LocalData.getPhoneNumber();
        this.appVersion = DeviceIDUtil.getAppVersion();
        this.deviceName = DeviceIDUtil.getDeviceName();
        this.channel = AppKey.CHANNEL;
    }

    public static String getUserDevicesJson(String apiEndPoint) {
        if (userDevices == null) {
            userDevices = new UserDevices();
        }

        if (!userDevices.uuid.equals(LocalData.getUserUuid())) {
            userDevices = new UserDevices();
        }

        userDevices.uuid = LocalData.getUserUuid();
        userDevices.phoneNumber = LocalData.getPhoneNumber();
        userDevices.appVersion = DeviceIDUtil.getAppVersion();
        userDevices.deviceName = DeviceIDUtil.getDeviceName();
        userDevices.apiEndPoint = apiEndPoint;

        return JsonUtil.getJsonStringFromObject(userDevices);
    }


}

package com.theroyalsoft.mydoc.apputil.devices;

import android.annotation.SuppressLint;
import android.provider.Settings;

import com.theroyalsoft.mydoc.apputil.AppUtilConfig;

public class DevicesInfo {


    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        return Settings.Secure.getString(AppUtilConfig.getAppContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}

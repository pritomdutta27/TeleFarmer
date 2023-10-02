package com.theroyalsoft.telefarmer.utils;

import static com.theroyalsoft.telefarmer.TeleFarmerApp.getAppContext;

import android.os.Build;
import android.provider.Settings;

/**
 * Created by Pritom Dutta on 5/8/23.
 */

public class DeviceIDUtil {
    private static String deviceID = "";
    private static String deviceName = "";
    private static String appVersion = "";
    private static String osVersion = "";

    public static String getDeviceID() {
        if (deviceID.isEmpty()) {
            deviceID = Settings.Secure.getString(getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceID;
    }


    public static String getDeviceName() {
        if (deviceName.isEmpty()) {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            deviceName = StringUtil.getCapitalizeSentences((manufacturer) + " [" + model + "]");
        }
        return deviceName;
    }

    public static String getOsVersion() {
        if (osVersion.isEmpty()) {
            String name = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            osVersion = "[" + Build.VERSION.CODENAME + "-" + name + "-" + Build.VERSION.SDK_INT + "]";
        }
        return osVersion;
    }

//    public static String getAppVersion() {
//
//        if (appVersion.isEmpty()) {
//            String appVersionName = AppUser.BUILD_MODE.equals(AppUser.PRODUCTION) ? BuildConfig.VERSION_NAME : BuildConfig.VERSION_NAME + "(D)";
//            appVersion = appVersionName + " [" + BuildConfig.VERSION_CODE + "]";
//        }
//        return appVersion;
//    }
}

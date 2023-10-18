package bio.medico.patient.common;

/**
 * Created by Samiran Kumar on 08,August,2023
 **/
public class DeviceIDUtil {

    private static String deviceID = "";
    private static String deviceName = "";
    private static String appVersion = "";
    private static String osVersion = "";
    private static int appBuildVersionCode = 0;


    public static void setData(String deviceID, String deviceName, String appVersion, String osVersion,int appBuildVersionCode) {
        DeviceIDUtil.deviceID = deviceID;
        DeviceIDUtil.deviceName = deviceName;
        DeviceIDUtil.appVersion = appVersion;
        DeviceIDUtil.osVersion = osVersion;
        DeviceIDUtil.appBuildVersionCode = appBuildVersionCode;
    }

    public static String getDeviceID() {
        return deviceID;
    }


    public static String getDeviceName() {
        return deviceName;
    }

    public static String getOsVersion() {
        return osVersion;
    }

    public static String getAppVersion() {
        return appVersion;
    }


    public static int getAppBuildVersionCode() {
        return appBuildVersionCode;
    }

}

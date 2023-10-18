package bio.medico.patient.model.apiResponse;

import java.util.List;

/**
 * Created by Samiran Kumar on 25,July,2022
 **/
public class ResponseTrustedDevicesList {


    private List<DeviceList> deviceList;
    private boolean isSuccess;


    public List<DeviceList> getDeviceList() {
        return deviceList;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public static class DeviceList {
        private String userType;
        private String createAt;
        private String updatedAt;
        private boolean is_trusted;
        private boolean is_login;
        private String deviceId;
        private String phoneNumber;
        private String channel;
        private String _rev;
        private String _id;
        private String deviceName;

        public String getCreateAt() {
            return createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public String getUserType() {
            return userType;
        }

        public boolean isIs_trusted() {
            return is_trusted;
        }

        public boolean isIs_login() {
            return is_login;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getChannel() {
            return channel;
        }
    }


}

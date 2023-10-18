package com.theroyalsoft.telefarmer.model;

import com.skh.hkhr.util.NullRemoveUtil;

//import bio.medico.patient.data.local.LocalData;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class ResponseSingleDoctor {

    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public static class Doctor {
        private String bmdcNumber;
        private String address;
        private String phoneNumber;
        private String email;
        private String name;
        private String manualStatus;
        private String updatedAt;
        private String createAt;
        private String xmppStatus;
        private String xmppId;
        private String iosDeviceId;
        private String image;
        private String uuid;
        private String channel;
        private String androidDeviceId;
        private boolean isPushCall;

        public boolean getIsPushCall() {
            return isPushCall;
        }

        public void setIsPushCall(boolean isPushCall) {
            this.isPushCall = isPushCall;
        }

        public String getBmdcNumber() {
            return bmdcNumber;
        }

        public String getAddress() {
            return NullRemoveUtil.getNotNull(address);
        }

        public String getPhoneNumber() {
            return NullRemoveUtil.getNotNull(phoneNumber);
        }

        public String getEmail() {
            return NullRemoveUtil.getNotNull(email);
        }

        public String getName() {
            return NullRemoveUtil.getNotNull(name);
        }

        public String getManualStatus() {
            return manualStatus;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreateAt() {
            return createAt;
        }

        public String getXmppStatus() {
            return xmppStatus;
        }

        public String getXmppId() {
            return xmppId;
        }

        public String getIosDeviceId() {
            return iosDeviceId;
        }

        public String getImage() {
            return "LocalData.getImgBaseUrl()" + NullRemoveUtil.getNotNull(image);
        }

        public String getUuid() {
            return NullRemoveUtil.getNotNull(uuid);
        }

        public String getChannel() {
            return channel;
        }

        public String getAndroidDeviceId() {
            return androidDeviceId;
        }
    }
}

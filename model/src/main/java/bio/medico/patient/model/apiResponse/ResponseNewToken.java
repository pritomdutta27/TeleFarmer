package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;
import bio.medico.patient.model.StringUtil;

public class ResponseNewToken {

    private String accessToken;
    private UserInfo userInfo;

    public String getAccessToken() {
        return NullRemoveUtil.getNotNull(accessToken);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public class UserInfo {
        private String id;
        private String rev;
        private String androidDeviceID;
        private String channel;
        private String email;
        private String uuid;
        private String image;
        private String iosDeviceID;
        private String manualStatus;
        private String name;
        private String phoneNumber;
        private String xmppId;
        private String xmppStatus;
        private String createAt;
        private String updatedAt;


        public String getId() {
            return NullRemoveUtil.getNotNull(id);
        }

        public String getRev() {
            return rev;
        }

        public String getAndroidDeviceID() {
            return androidDeviceID;
        }

        public String getChannel() {
            return channel;
        }

        public String getEmail() {
            return email;
        }

        public String getUuid() {
            return NullRemoveUtil.getNotNull(uuid);
        }

        public String getImage() {
            return NullRemoveUtil.getNotNull(image);
        }

        public String getIosDeviceID() {
            return iosDeviceID;
        }

        public String getManualStatus() {
            return manualStatus;
        }

        public String getName() {
            return StringUtil.capitalizeFirstWord(NullRemoveUtil.getNotNull(name));
        }

        public String getPhoneNumber() {
            return NullRemoveUtil.getNotNull(phoneNumber);
        }

        public String getXmppId() {
            return NullRemoveUtil.getNotNull(xmppId);
        }

        public String getXmppStatus() {
            return xmppStatus;
        }

        public String getCreateAt() {
            return createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setRev(String rev) {
            this.rev = rev;
        }

        public void setAndroidDeviceID(String androidDeviceID) {
            this.androidDeviceID = androidDeviceID;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setIosDeviceID(String iosDeviceID) {
            this.iosDeviceID = iosDeviceID;
        }

        public void setManualStatus(String manualStatus) {
            this.manualStatus = manualStatus;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setXmppId(String xmppId) {
            this.xmppId = xmppId;
        }

        public void setXmppStatus(String xmppStatus) {
            this.xmppStatus = xmppStatus;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }


}

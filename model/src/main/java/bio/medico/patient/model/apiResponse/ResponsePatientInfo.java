package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;
import bio.medico.patient.model.StringUtil;

public class ResponsePatientInfo {

    private String location;
    private String height;
    private String weight;
    private String dob;
    private String updatedAt;
    private String createAt;
    private String xmppStatus;
    private String xmppId;
    private String phoneNumber;
    private String name;
    private String manualStatus;
    private String iosDeviceId;
    private String uuid;
    private String channel;
    private String androidDeviceId;
    private String gender;
    private String image;




    public String getImage() {
        return NullRemoveUtil.getNotNull(image);
    }


    public String getLocation() {
        return NullRemoveUtil.getNotNull(location);
    }

    public String getHeight() {
        return NullRemoveUtil.getNotNull(height);
    }

    public String getGender() {
        return NullRemoveUtil.getNotNull(gender);
    }


    public String getWeight() {
        return NullRemoveUtil.getNotNull(weight);
    }

    public String getDob() {
        return NullRemoveUtil.getNotNull(dob);
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

    public String getPhoneNumber() {
        return NullRemoveUtil.getNotNull(phoneNumber);
    }

    public String getName() {
        return StringUtil.capitalizeFirstWord(NullRemoveUtil.getNotNull(name));
    }

    public String getUuid() {
        return NullRemoveUtil.getNotNull(uuid);
    }

    public String getChannel() {
        return channel;
    }

}
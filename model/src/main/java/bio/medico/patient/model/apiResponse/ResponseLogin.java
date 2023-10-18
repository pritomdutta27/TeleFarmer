package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;


public class ResponseLogin {
    private boolean isSuccess;
    private boolean isVerified;
    private boolean isProfile;
    private String accessToken;
    private String phoneNumber;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    public void setProfile(boolean profile) {
        this.isProfile = profile;
    }
    public boolean isProfile() {
        return isProfile;
    }


    public String getAccessToken() {
        return NullRemoveUtil.getNotNull(accessToken);
    }

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }


}

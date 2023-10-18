package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

/**
 * Created by Samiran Kumar on 29,September,2022
 **/
public class ResponseOtpVerify {
    private boolean isSuccess;
    private String message;
    private String accessToken; //new field

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }

    public String getAccessToken() {
        return NullRemoveUtil.getNotNull(accessToken);
    }
}

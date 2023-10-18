package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

/**
 * Created by Samiran Kumar on 18,September,2022
 **/
public class ResponseUnsubscribe {

    private boolean isSuccess;
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }
}

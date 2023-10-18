package bio.medico.patient.data;

import com.skh.hkhr.util.NullRemoveUtil;

public class APIError {
    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }



    ///===============================================================


}

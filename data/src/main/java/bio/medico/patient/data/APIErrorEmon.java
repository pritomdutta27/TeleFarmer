package bio.medico.patient.data;

import com.skh.hkhr.util.NullRemoveUtil;

public class APIErrorEmon {
    private boolean isSuccess;
    private int statusCode;
    private String message;
    private String instructionUrl;

    public APIErrorEmon() {
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }

    public String getInstruction_url() {
        instructionUrl = NullRemoveUtil.getNotNull(instructionUrl);
        if (instructionUrl.isEmpty()) {
            instructionUrl = "https://medico.bio/instructions";
        }
        return instructionUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }
///===============================================================


}

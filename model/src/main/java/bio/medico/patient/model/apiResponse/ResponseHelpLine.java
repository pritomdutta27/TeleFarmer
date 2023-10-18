package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

public class ResponseHelpLine {

    private String longCodeNumber;

    public String getLongCodeNumber() {
        return NullRemoveUtil.getNotNull(longCodeNumber);
    }
}
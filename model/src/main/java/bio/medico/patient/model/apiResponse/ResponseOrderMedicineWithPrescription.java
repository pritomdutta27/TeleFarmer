package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

public class ResponseOrderMedicineWithPrescription {

    private String message;

    public String getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }
}

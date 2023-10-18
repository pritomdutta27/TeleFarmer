package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

public class ResponseCallHistoryModel {
    private List<ResponseCallHistory> callHistory;

    public List<ResponseCallHistory> getCallHistory() {
        return NullRemoveUtil.getNotNull(callHistory);
    }
}

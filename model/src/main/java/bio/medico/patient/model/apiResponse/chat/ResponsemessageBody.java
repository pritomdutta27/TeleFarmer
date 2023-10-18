package bio.medico.patient.model.apiResponse.chat;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

/**
 * Created by Samiran Kumar on 11,October,2022
 **/
public class ResponsemessageBody {
    private List<MessageBody> message;

    public List<MessageBody> getMessage() {
        return NullRemoveUtil.getNotNull(message);
    }
}

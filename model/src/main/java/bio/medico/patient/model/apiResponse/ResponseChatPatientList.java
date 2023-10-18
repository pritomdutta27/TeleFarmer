package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

/**
 * Created by Samiran Kumar on 26,October,2022
 **/
public class ResponseChatPatientList {

    private List<ChatPatien> user;

    public List<ChatPatien> getUser() {
        return NullRemoveUtil.getNotNull(user);
    }

    public static class ChatPatien {
        private String senderId;
        private String senderName;
        private String conversationId;
        private String body;
        private String dateAndTime;
        private String senderImage;
        private String status;

        public String getSenderId() {
            return senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public String getConversationId() {
            return conversationId;
        }

        public String getBody() {
            return body;
        }

        public String getDateAndTime() {
            return dateAndTime;
        }

        public String getSenderImage() {
            return senderImage;
        }

        public String getStatus() {
            return status;
        }
    }

}

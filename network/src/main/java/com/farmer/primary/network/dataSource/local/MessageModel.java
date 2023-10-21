package com.farmer.primary.network.dataSource.local;

import java.util.Date;


import bio.medico.patient.model.apiResponse.chat.MessageBody;
import timber.log.Timber;

/**
 * Created by Samiran Kumar on 13,August,2023
 **/
public class MessageModel {

    public static String doctorName = "Medico Support";
    public static String doctorId = "customer1care12344321";
    // private static String doctorId = "customerCareTest";


    static String SenderTYPE_DOCTOR = "DOCTOR";
    static String SenderTYPE_PATIENT = "PATIENT";

    static String SenderTYPE = SenderTYPE_PATIENT;

    public static String getSenderID() {
        return LocalData.getUserUuid();
    }


    public static String getrecipientID() {
        return doctorId;
    }

    public static void setrecipientID(String id) {
        Timber.e("setrecipientID:" + id);
        //  patientId = id;
    }

    public static String getConversationID() {
        return doctorId + "_" + LocalData.getUserUuid();
    }



//    public static MessageBody getModel(String attachmentTypeImage, String attachment) {
//        return getDataModel(attachmentTypeImage, attachment, AttachmentTypes.statusTrue, "");
//    }
//
//    public static MessageBody getModel(String messageBody) {
//        return getDataModel("", "", AttachmentTypes.statusFalse, messageBody);
//    }


    private static MessageBody getDataModel(String attachmentType1, String attachment1, int isAttachment1, String body1) {

        long id = new Date().getTime();
        String conversationId =getConversationID();
        String recipientId = getrecipientID();

        String recipientName = doctorName;

        String recipientImage = "";
        String senderId = LocalData.getUserUuid();
        String senderName = LocalData.getUserName();
        String senderImage = LocalData.getUserProfile();
        String attachmentType = attachmentType1;
        String attachment = attachment1;
        int isAttachment = isAttachment1;
        String body = body1;
        String dateAndTime = "";
        String dateText = "";
//        String status = AttachmentTypes.Status_Unseen; // seen unseen
//        int delivered = AttachmentTypes.statusFalse;


//        return new MessageBody(
//                id, conversationId, recipientId, recipientName,
//                recipientImage, senderId, senderName, senderImage,
//                attachmentType, attachment, isAttachment, body, dateAndTime,
//                dateText, status, delivered
//        );
        return new MessageBody(
                id, conversationId, recipientId, recipientName,
                recipientImage, senderId, senderName, senderImage,
                attachmentType, attachment, isAttachment, body, dateAndTime,
                dateText, "", 0
        );
    }


}

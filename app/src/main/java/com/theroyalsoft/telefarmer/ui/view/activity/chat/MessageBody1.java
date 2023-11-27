package com.theroyalsoft.telefarmer.ui.view.activity.chat;


import com.farmer.primary.network.dataSource.local.LocalData;

import java.util.Date;

import bio.medico.patient.model.NullRemoveUtil;
import bio.medico.patient.common.AttachmentTypes;
import timber.log.Timber;

/**
 * Created by Samiran Kumar on 11,October,2022
 **/
public class MessageBody1 {

    private long id;
    private String conversationId;

    private String recipientId;
    private String recipientName;
    private String recipientImage;

    private String senderId;
    private String senderName;
    private String senderImage;

    private String attachmentType = "";
    private String attachment = "";
    private int isAttachment = AttachmentTypes.statusFalse;
    private String body;
    private String dateAndTime;

    private String dateText;




    private String status = AttachmentTypes.Status_Unseen; // seen unseen
    private int delivered = AttachmentTypes.statusFalse;



    /*    {
        "recipientId":"8cf5657f-16c4-404d-bcc1-01656874656b", "recipientImage":
        "ddsfgsd", "recipientName":"sdgsd", "senderName":"mahadi hasan", "id":0, "body":
        "hello ", "isAttachment":false, "senderId":"customer1care12344321", "delivered":
        true, "senderImage":"ee", "dateAndTime":"ee", "conversationId":
        "customer1care12344321_8cf5657f-16c4-404d-bcc1-01656874656b", "status":"unseen"
    }*/

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


    private MessageBody1() {

        conversationId = getConversationID();
        senderId = LocalData.getUserUuid();
        senderName = LocalData.getUserName();
        senderImage = LocalData.getUserProfile();

        recipientId = getrecipientID();
        recipientName = doctorName;
        recipientImage = "";

        id = new Date().getTime();
        //dateAndTime = AppKey.getTime4(AppKey.DATE_TIME_FORmMATE_1);
    }

    public MessageBody1(String body) {
        this();
        this.body = body;
    }

    public MessageBody1(String attachmentType, String attachment) {
        this();
        this.body = "";
        this.attachmentType = attachmentType;
        this.attachment = attachment;
        this.isAttachment = AttachmentTypes.statusTrue;

        id = (int) new Date().getTime();
        //\dateAndTime = AppKey.getTime4(AppKey.DATE_TIME_FORmMATE_1);
    }



    public String getAttachmentType() {
        return NullRemoveUtil.getNotNull(attachmentType);
    }

    public String getAttachment() {
        return NullRemoveUtil.getNotNull(attachment);
    }

    public String getStatus() {
        return status;
    }

    public int getDelivered() {
        return delivered;
    }

    public int getIsAttachment() {
        return isAttachment;
    }

    public String getBody() {
      /*  String aaa = NullRemoveUtil.getNotNull(body);
        if (aaa.isEmpty() || isAttachment == AttachmentTypes.statusTrue) {
            body = "Image Found";
        }*/

        return NullRemoveUtil.getNotNull(body);
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public String getSenderName() {
        return NullRemoveUtil.getNotNull(senderName);
    }

    public String getSenderId() {
        return NullRemoveUtil.getNotNull(senderId);
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientImage() {
        String recipientImageUrl = "";
        if (!NullRemoveUtil.getNotNull(recipientImage).isEmpty()) {
            recipientImageUrl = LocalData.getImgBaseUrl() + NullRemoveUtil.getNotNull(recipientImage);
        }
        return recipientImageUrl;
    }


    public String getRecipientId() {
        return recipientId;
    }

    public long getId() {
        return id;
    }

    public String getDateAndTime() {
        return NullRemoveUtil.getNotNull(dateAndTime);
    }

    public boolean isSent() {
        return true;
    }

    public boolean isDelivered() {
        return getDelivered() == 1;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getDateText() {
        return NullRemoveUtil.getNotNull(dateText);
    }
}

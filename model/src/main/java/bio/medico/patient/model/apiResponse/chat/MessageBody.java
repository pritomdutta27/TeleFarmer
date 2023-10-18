package bio.medico.patient.model.apiResponse.chat;

import bio.medico.patient.model.NullRemoveUtil;

/**
 * Created by Samiran Kumar on 11,October,2022
 **/
public class MessageBody {

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
    private int isAttachment;
    private String body;
    private String dateAndTime;

    private String dateText;


    private String status;
    private int delivered;

    public MessageBody(long id, String conversationId, String recipientId, String recipientName, String recipientImage, String senderId, String senderName, String senderImage, String attachmentType, String attachment, int isAttachment, String body, String dateAndTime, String dateText, String status, int delivered) {
        this.id = id;
        this.conversationId = conversationId;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientImage = recipientImage;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderImage = senderImage;
        this.attachmentType = attachmentType;
        this.attachment = attachment;
        this.isAttachment = isAttachment;
        this.body = body;
        this.dateAndTime = dateAndTime;
        this.dateText = dateText;
        this.status = status;
        this.delivered = delivered;
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


    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientImage() {

        return NullRemoveUtil.getNotNull(recipientImage);
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

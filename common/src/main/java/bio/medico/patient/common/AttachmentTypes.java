package bio.medico.patient.common;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AttachmentTypes {

    public static final int IMAGE = 2;
    public static final int VIDEO = 3;
    public static final int DOC = 4;

    public static final int NONE_TEXT = 6;
    public static final int NONE_TYPING = 7;

    public static String statusSeen = "seen";
    public static String Status_Unseen = "unseen";

    public static int statusFalse = 0;
    public static int statusTrue = 1;

    public static String attachmentTypeDoc = "doc";
    public final static String attachmentTypeImage = "image";
    public static String attachmentTypeVideo = "video";


    public static int getTypeName(String attachmentType) {
        switch (attachmentType) {
            case attachmentTypeImage:
                return IMAGE;

            default:
                return 0;
        }
    }

    @IntDef({IMAGE, VIDEO, DOC, NONE_TEXT, NONE_TYPING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttachmentType {
    }

    public static String getTypeName(@AttachmentType int attachmentType) {

        switch (attachmentType) {
            case IMAGE:
                return attachmentTypeImage;
            case VIDEO:
                return attachmentTypeVideo;
            case DOC:
                return attachmentTypeDoc;


            case NONE_TEXT:
                return "none_text";
            case NONE_TYPING:
                return "none_typing";
            default:
                return "none";
        }
    }
}

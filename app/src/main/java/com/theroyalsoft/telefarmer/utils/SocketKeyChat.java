package com.theroyalsoft.telefarmer.utils;

import com.farmer.primary.network.utils.NullRemoveUtil;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class SocketKeyChat {


    public final static String LISTENER_USER_INFO = "user_id";
    public final static String LISTENER_message_send = "message-send";
    public final static String LISTENER_message_received = "message-received";
    public final static String LISTENER_user_notify = "user-notify";
    public final static String LISTENER_callback = "callback";




    //===============================================
    public static String RECEIVER_ID = "";

    private static String receiverDeviceId = "";

    public static void setReceiverDeviceId(String fromDeviceID) {
        Timber.e("setReceiverDeviceId : " + fromDeviceID);
        receiverDeviceId = fromDeviceID;
    }

    public static String getReceiverDeviceId() {
        return NullRemoveUtil.getNotNull(receiverDeviceId);
    }
}

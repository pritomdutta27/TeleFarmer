package com.theroyalsoft.telefarmer.utils;

import com.farmer.primary.network.utils.NullRemoveUtil;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class SocketKey {


    public final static String LISTENER_USER_INFO = "user_id";
    public final static String LISTENER_PRE_OFFER = "pre-offer";
    public final static String LISTENER_PRE_OFFER_ANSWER = "pre-offer-answer";
    public final static String LISTENER_USER_HANGUP = "user-hanged-up";
    public final static String LISTENER_DATA_WEBRTC_SIGNALING = "webRTC-signaling";
    public final static String LISTENER_MANAGE_UI = "manage-ui";

    public final static String KEY_TYPE_VIDEO_PERSONAL_CODE = "VIDEO_PERSONAL_CODE";
    public final static String KEY_TYPE_AUDIO_PERSONAL_CODE = "AUDIO_PERSONAL_CODE";
    public final static String SOCKET_TYPE_PRE_OFFER = LISTENER_PRE_OFFER;
    public final static String LISTENER_OFFER_ALREADY_RECEIVED = "offer-already-received";


    public final static String KEY_TYPE_CALL_ACCEPTED = "CALL_ACCEPTED";
    public final static String KEY_TYPE_OFFER = "OFFER";
    public final static String KEY_TYPE_ANSWER = "ANSWER";
    public final static String KEY_TYPE_CANDIDATE = "ICE_CANDIDATE";
    public final static String KEY_TYPE_CALL_HANGED_UP = "CALL_HANGED_UP";
    public final static String KEY_CALL_REJECTED_FORCE = "CALL_REJECTED_FORCE";
    public final static String KEY_TYPE_CALL_REJECTED = "CALL_REJECTED";
    public final static String KEY_TYPE_CALL_ENDED = "CALL_ENDED";
    public final static String KEY_TYPE_CALLEE_NOT_FOUND = "CALLEE_NOT_FOUND";
    public final static String KEY_TYPE_CALLEE_NO_ANSWER = "CALLEE_NO_ANSWER";
    public final static String KEY_TYPE_VIDEO_ON = "VIDEO_ON";
    public final static String KEY_TYPE_VIDEO_OFF = "VIDEO_OFF";

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

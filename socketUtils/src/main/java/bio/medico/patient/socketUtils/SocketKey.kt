package bio.medico.patient.socketUtils

import bio.medico.patient.model.NullRemoveUtil
import timber.log.Timber

object SocketKey {
    const val LISTENER_USER_INFO = "user_id"
    const val LISTENER_PRE_OFFER = "pre-offer"
    const val LISTENER_PRE_OFFER_ANSWER = "pre-offer-answer"
    const val LISTENER_USER_HANGUP = "user-hanged-up"
    const val LISTENER_DATA_WEBRTC_SIGNALING = "webRTC-signaling"
    const val LISTENER_MANAGE_UI = "manage-ui"
    const val KEY_TYPE_VIDEO_PERSONAL_CODE = "VIDEO_PERSONAL_CODE"
    const val KEY_TYPE_AUDIO_PERSONAL_CODE = "AUDIO_PERSONAL_CODE"
    const val SOCKET_TYPE_PRE_OFFER = LISTENER_PRE_OFFER
    const val LISTENER_OFFER_ALREADY_RECEIVED = "offer-already-received"
    const val KEY_TYPE_CALL_ACCEPTED = "CALL_ACCEPTED"
    const val KEY_TYPE_OFFER = "OFFER"
    const val KEY_TYPE_ANSWER = "ANSWER"
    const val KEY_TYPE_CANDIDATE = "ICE_CANDIDATE"
    const val KEY_TYPE_CALL_HANGED_UP = "CALL_HANGED_UP"
    const val KEY_CALL_REJECTED_FORCE = "CALL_REJECTED_FORCE"
    const val KEY_TYPE_CALL_REJECTED = "CALL_REJECTED"
    const val KEY_TYPE_CALL_ENDED = "CALL_ENDED"
    const val KEY_TYPE_CALLEE_NOT_FOUND = "CALLEE_NOT_FOUND"
    const val KEY_TYPE_CALLEE_NO_ANSWER = "CALLEE_NO_ANSWER"
    const val KEY_TYPE_VIDEO_ON = "VIDEO_ON"
    const val KEY_TYPE_VIDEO_OFF = "VIDEO_OFF"

    //===============================================
    @JvmField
    var RECEIVER_ID = ""
    @JvmStatic
    var receiverDeviceId = ""
        get() = NullRemoveUtil.getNotNull(field)
        set(fromDeviceID) {
            Timber.e("setReceiverDeviceId : $fromDeviceID")
            field = fromDeviceID
        }
}
package bio.medico.patient.common


/**
Created by Samiran Kumar on 13,August,2023
 **/

object AppKey {


    const val INTENT_VIDEO_CALL = "INTENT_VIDEO_CALL"
    const val INTENT_FREE_CALL = "INTENT_FREE_CALL"
    const val INTENT_FREE_CALL_INFO = "INTENT_FREE_CALL_INFO"

    // String INTENT_FREE_CALL_DURATION = "INTENT_FREE_CALL_DURATION"

    const val INTENT_FROM_PRESCRIPTION_NOTIFICATION = "INTENT_FROM_PRESCRIPTION_NOTIFICATION"
    const val INTENT_PRESCRIPTION_ID = "INTENT_PRESCRIPTION_ID"
    const val INTENT_BILLING_URL = "INTENT_BILLING_URL"
    const val INTENT_FREE_CALL_DURATION = "INTENT_FREE_CALL_DURATION"
    const val INTENT_DETAILS = "INTENT_DETAILS"
    const val INTENT_DATA = "INTENT_DATA"
    const val INTENT_TITLE = "INTENT_TITLE"
    const val INTENT_IMAGE = "INTENT_IMAGE"
    const val INTENT_UI = "INTENT_UI"
    const val INTENT_URL = "INTENT_URL"

    const val INTENT_ROOM_ID = "INTENT_ROOM_ID"
    const val INTENT_doctorId = "INTENT_doctorId"
    const val INTENT_PatientId = "INTENT_PatientId"
    const val INTENT_scheduleId = "INTENT_scheduleId"
    const val INTENT_JWT = "INTENT_JWT"
    const val INTENT_QUEUE_STATUS = "INTENT_QUEUE_STATUS"


    const val INTENT_KEY_MSISDN = "MSISDN"
    const val INTENT_KEY_NAME = "NAME"
    const val INTENT_KEY_EN = "EN"
    const val INTENT_KEY_BN = "BN"
    const val INTENT_KEY_LAB_TEST = "LAB_TEST"

    const val UI_NOTIFICATION = "UI_NOTIFICATION"
    const val UI_ORDER_MEDICINE = "UI_ORDER_MEDICINE"
    const val UI_BUY_PACK = "UI_BUY_PACK"

    const val RESULT_CODE_BUY_PACK = 101


    /*

    medico://home
    medico://home/payment
    medico://home/my-health
    medico://prescription
    medico://call-history
    medico://my-details
    medico://upload-prescription
    medico://upload-lab-report
    medico://current-medication
    medico://previous-surgery
    medico://medicine-reminder
    medico://add-medicine-reminder
    medico://chat
    medico://discount

     <string name="scheme_medico">medico</string>
    <string name="deeplink_home">home</string>
    <string name="deeplink_payment">payment</string> <!-- medico://home/payment -->
    <string name="deeplink_my_health">my-health</string>   <!-- medico://home/my-health -->
    <string name="deeplink_prescription">prescription</string>
    <string name="deeplink_call_history">call-history</string>
    <string name="deeplink_my_details">my-details</string>
    <string name="deeplink_upload_prescription">upload-prescription</string>
    <string name="deeplink_upload_lab_report">upload-lab-report</string>
    <string name="deeplink_current_medication">current-medication</string>
    <string name="deeplink_previous_surgery">previous-surgery</string>
    <string name="deeplink_medicine_reminder">medicine-reminder</string>
    <string name="deeplink_add_medicine_reminder">add-medicine-reminder</string>
    <string name="deeplink_chat">chat</string>
    <string name="deeplink_discount">discount</string>
    * */
    //=======================================================
    // DEEP_LINK-------------------------------------
    const val DEEP_LINK_PAYMENT = "/payment"
    const val DEEP_LINK_MY_HEALTH = "/my-health"
    const val DEEP_LINK_buy_medicine = "/buy_medicine"
    const val DEEP_LINK_call_ambulance = "/call_ambulance"
    const val DEEP_LINK_call_nurse = "/call_nurse"
    const val DEEP_LINK_call_physiotherapy = "/call_physiotherapy"

    //=======================================================
    const val CHANNEL = "android"
    const val LANGUAGE_EN = "EN"
    const val LANGUAGE_BN = "BN"

    const val isDebugTestingStatus = false
    const val subStatusDebug = false


    const val loading_signing = "Signing.."
    const val loading_doctors_find = "Doctors finding.."
    const val Searching_doctor = "Searching Doctors"
    const val DELETE_LOADING = "Deleting.."
    const val UPLOADING = "Uploading.."
    const val FETCHING_PRESCRIPTION = "Fetching Prescription Information"
    const val Calling_to = "Calling to "
    const val Patient_in_call = "Can't place a new call while you're already in a call."


    const val ERROR_INTERNET_CONNECTION =
        "Internet not available! Please connect to the Internet and try again!"
    const val ACTIVE = "ACTIVE"
    const val MEDICATION = "CURRENT_MEDICATION"
    const val PREVIOUS_SURGERY = "PREVIOUS_SURGERY"


    const val TEXT_MEDICINE = "Prescribe Medicine"
    const val TEXT_MEDICINE_BN = "প্রেসক্রিপশনের ওষুধ"

    const val TEXT_PRIVACY =
        "<p>I have read and agree to the <span style=\"color: #017BE6;\"><a href=\"https://medico.bio/terms-conditions\">Terms Conditions</a>, <a href=\"https://medico.bio/privacyPolicy\">Privacy Policy</a></span> and <span style=\"color: #017BE6;\"> <a href=\"https://medico.bio/privacyPolicy\">Return/refund-policy</a></span></p>"
    const val TEXT_PRIVACY_BN =
        "<p>আমি পড়েছি এবং এর সাথে একমত <span style=\"color: #008080;\"><a href=\"https://medico.bio/terms-conditions\">শর্তাবলী</a>, <a href=\"https://medico.bio/privacyPolicy\">গোপনীয়তা নীতি </a></span> এবং <span style=\"color: #008080;\"><a href=\"https://medico.bio/privacyPolicy\">রিটার্ন/ফেরত নীতি</a></span></p>"

    const val IVR_AMBULANCE_NUMBER = "+8801847050796"
    const val IVR_NURSING_NUMBER = "+8801844476999"
    const val IVR_Physio_NUMBER = "+8801844476988"
    const val LONG_CODE_NUMBER_HELPLINE = "09613300333"


    const val ORDER_TYPE_MEDICINE = "ORDER_TYPE_MEDICINE"
    const val ORDER_TYPE_PRESCRIPTION = "IMAGE_PRESCRIPTION"
    const val ORDER_TYPE_MEDICO_PRESCRIPTION = "MEDICO_PRESCRIPTION"
    const val IMAGE_ATTACHMENT_FILE = "no"

    const val TYPE_ADVICE = "TYPE_ADVICE"
    const val TYPE_ASSESSMENT = "TYPE_ASSESSMENT"
    const val TYPE_INVESTIGATION = "TYPE_INVESTIGATION"
    const val TEXT_ADVICE = "Advice"
    const val TEXT_ASSESSMENT = "Assessment"
    const val TEXT_INVESTIGATION = "Investigation"

    //=========================================================
    const val TIMER_NOT_FOUND_DOCTOR_API = (3 * 1000).toLong()
    const val TIMER_RECALL_DOCTOR_API = (5 * 1000).toLong()
    const val TIMER_CONNECTING_TIME = (200 * 1000).toLong()
    const val TIMER_RINGING_TIME = (150 * 1000).toLong()
    const val TIMER_PRESENCES_CHECK_TIME = (23 * 1000).toLong()

    const val AUTO_RENEWAL = "auto-renewal"
    const val USER_PATIENT = "patient"

    const val USER_DOCTOR = "DOCTOR"
    const val OTP_RESEND = "OTP_RESEND"
    const val FORGOT = "forgot"
    const val NEW_USER = "newUser"

    const val CHAT_FOLDER = "chat"
    const val LAB_REPORT = "labReport"
    const val PATH_PRESCRIPTION_ATTACHMENT = "prescriptionAttachment"
    const val PATIENT_FOLDER_ORDER_PRESCRIPTION = "orderPrescription"

    const val REMINDER_MODEL = "REMINDER"
    const val REMINDER_ID = "REMINDER_ID"
    const val MSISDN = "msisdn"
    const val ADDITIONAL = "additional"

    const val PUSH_KEY_APP_UPDATE = "app_update"
    const val PUSH_KEY_PRESCRIPTION = "prescription"
    const val PUSH_KEY_CHAT_MESSAGE = "chat_message"
    const val PUSH_KEY_Logout = "logout"
    const val PUSH_KEY_APP_PROMOTION = "app_promotion"
    const val PUSH_KEY_payment = "payment"

    //=========================================================

    //=========================================================
    //===========firebase event============================
    const val Event_Ambulance = "EVENT_AMBULANCE_CALL_CLICK"
    const val EVENT_LOGIN_SCREEN_VIEW = "EVENT_LOGIN_SCREEN_VIEW"
    const val EVENT_SIGNUP_SCREEN_VIEW = "EVENT_SIGNUP_SCREEN_VIEW"
    const val EVENT_OTP_SCREEN_VIEW = "EVENT_OTP_SCREEN_VIEW"
    const val EVENT_FORGET_SCREEN_VIEW = "EVENT_FORGET_SCREEN_VIEW"
    const val EVENT_SET_NEW_PASSWORD_SCREEN_VIEW = "EVENT_SET_NEW_PASSWORD_SCREEN_VIEW"
    const val EVENT_HOME_SCREEN_VIEW = "EVENT_HOME_SCREEN_VIEW"
    const val EVENT_PAYMENT_SCREEN_VIEW = "EVENT_PAYMENT_SCREEN_VIEW"
    const val EVENT_MY_HEALTH_SCREEN_VIEW = "EVENT_MY_HEALTH_SCREEN_VIEW"
    const val EVENT_MY_DETAILS_SCREEN_VIEW = "EVENT_MY_DETAILS_SCREEN_VIEW"
    const val EVENT_PREVIOUS_PRESCRIPTION_SCREEN_VIEW = "EVENT_PREVIOUS_PRESCRIPTION_SCREEN_VIEW"
    const val EVENT_PREVIOUS_SURGERY_SCREEN_VIEW = "EVENT_PREVIOUS_SURGERY_SCREEN_VIEW"
    const val EVENT_RUNNING_MEDICINE_SCREEN_VIEW = "EVENT_RUNNING_MEDICINE_SCREEN_VIEW"
    const val EVENT_PRESCRIPTION_LIST_SCREEN_VIEW = "EVENT_PRESCRIPTION_LIST_SCREEN_VIEW"
    const val EVENT_ORDER_HISTORY_SCREEN_VIEW = "EVENT_ORDER_HISTORY_SCREEN_VIEW"
    const val EVENT_CALL_HISTORY_SCREEN_VIEW = "EVENT_CALL_HISTORY_SCREEN_VIEW"
    const val EVENT_MEDICINE_REMINDER_SCREEN_VIEW = "EVENT_MEDICINE_REMINDER_SCREEN_VIEW"
    const val EVENT_ADD_MEDICINE_REMINDER_SCREEN_VIEW = "EVENT_ADD_MEDICINE_REMINDER_SCREEN_VIEW"
    const val EVENT_MEDICINE_ORDER_SCREEN_VIEW = "EVENT_MEDICINE_ORDER_SCREEN_VIEW"
    const val EVENT_INVESTIGATION_ORDER_SCREEN_VIEW = "EVENT_INVESTIGATION_ORDER_SCREEN_VIEW"
    const val EVENT_PRESCRIPTION_SCREEN_VIEW = "EVENT_PRESCRIPTION_SCREEN_VIEW"
    const val EVENT_PACKAGE_LIST_SCREEN_VIEW = "EVENT_PRESCRIPTION_SCREEN_VIEW"
    const val EVENT_CALL_SCREEN_VIEW = "EVENT_CALL_SCREEN_VIEW"
    const val EVENT_CHAT_SCREEN_VIEW = "EVENT_CHAT_SCREEN_VIEW"
    const val EVENT_PAYMENT_GATEWAY_SCREEN_VIEW = "EVENT_PAYMENT_GATEWAY_SCREEN_VIEW"
    const val EVENT_LAB_REPORT_SCREEN_VIEW = "EVENT_LAB_REPORT_SCREEN_VIEW"

    const val EVENT_LOGIN_CLICK = "EVENT_LOGIN_CLICK"
    const val EVENT_SIGNUP_CLICK = "EVENT_SIGNUP_CLICK"
    const val EVENT_FORGET_PASSWORD_CLICK = "EVENT_FORGET_PASSWORD_CLICK"
    const val EVENT_OTP_VERIFY_CLICK = "EVENT_OTP_VERIFY_CLICK"
    const val EVENT_RESET_PASSWORD_CLICK = "EVENT_RESET_PASSWORD_CLICK"
    const val EVENT_CALL_CLICK = "EVENT_CALL_CLICK"
    const val EVENT_CALL_CONNECTED = "EVENT_CALL_CONNECTED"
    const val EVENT_CALL_NO_ANSWER = "EVENT_CALL_NO_ANSWER"
    const val EVENT_CALL_END_CLICK = "EVENT_CALL_END_CLICK"
    const val EVENT_CALL_HANGUP_CLICK = "EVENT_CALL_HANGUP_CLICK"
    const val EVENT_SEND_CHAT_CLICK = "EVENT_SEND_CHAT_CLICK"
    const val EVENT_PACKAGE_CLICK = "EVENT_PACKAGE_CLICK"
    const val EVENT_GATEWAY_CLICK = "EVENT_GATEWAY_CLICK"
    const val EVENT_MEDICINE_ORDER_CLICK = "EVENT_MEDICINE_ORDER_CLICK"
    const val EVENT_MEDICINE_IMAGE_PRESCRIPTION_ORDER_CLICK =
        "EVENT_MEDICINE_IMAGE_PRESCRIPTION_ORDER_CLICK"
    const val EVENT_MEDICINE_MEDICO_PRESCRIPTION_ORDER_CLICK =
        "EVENT_MEDICINE_MEDICO_PRESCRIPTION_ORDER_CLICK"
    const val EVENT_INVESTIGATION_ORDER_CLICK = "EVENT_INVESTIGATION_ORDER_CLICK"
    const val EVENT_PUSH_NOTIFICATION = "PUSH_NOTIFICATION_UI_OPEN"
    const val EVENT_FORCE_UPDATE_DIALOG_OPEN = "EVENT_FORCE_UPDATE_DIALOG_OPEN"


    const val EVENT_NURSING_CALL_CLICK = "EVENT_NURSING_CALL_CLICK"
    const val EVENT_PHYSIO_CALL_CLICK = "EVENT_PHYSIO_CALL_CLICK"
    const val EVENT_LANGUAGE_CHANGE = "EVENT_LANGUAGE_CHANGE"

    const val EVENT_CLICK_UNSUBSCRIPTION = "EVENT_CLICK_UNSUBSCRIPTION"
    const val EVENT_SUCCESS_UNSUBSCRIPTION = "EVENT_SUCCESS_UNSUBSCRIPTION"
    const val EVENT_FAILED_UNSUBSCRIPTION = "EVENT_FAILED_UNSUBSCRIPTION"

    const val VIDEO_CALL = "VIDEO_CALL"
    const val AUDIO_CALL = "AUDIO_CALL"
}
package bio.medico.patient.data.local

object SpKey {

    const val SP_KEY_CONTENT_CACHE_KEY_SERVER = "CONTENT_CACHE_KEY_SERVER"
    const val SP_KEY_CONTENT_CACHE_KEY_LOCAL = "CONTENT_CACHE_KEY_LOCAL"
    const val SP_KEY_PHONE_NUMBER = "SP_KEY_PHONE_NUMBER"
    const val SP_KEY_LANGUAGE = "language"
    const val sp_userName = "sp_userName"
    const val sp_userProfile = "sp_userProfile"
    const val sp_userInfo = "sp_userInfo"
    const val sp_ResponseMetaInfo = "sp_ResponseMetaInfo"
    const val sp_ResponseSubInfo = "sp_sub_info"
    const val sp_drugs = "sp_drugs"
    const val sp_user_Info_token = "user_Info_token"
    const val sp_setUserProfileAll = "sp_setUserProfileAll"
    const val sp_Token = "user_token"
    const val sp_call_limit = "sp_call_limit"

    /*


  //  String INTENT_FROM_PRESCRIPTION_NOTIFICATION = "INTENT_FROM_PRESCRIPTION_NOTIFICATION";
  //  String INTENT_PRESCRIPTION_ID = "INTENT_PRESCRIPTION_ID";
    String INTENT_BILLING_URL = "INTENT_BILLING_URL";
    String INTENT_VIDEO_CALL = "INTENT_VIDEO_CALL";
    String INTENT_FREE_CALL = "INTENT_FREE_CALL";
    String INTENT_FREE_CALL_INFO = "INTENT_FREE_CALL_INFO";

   // String INTENT_FREE_CALL_DURATION = "INTENT_FREE_CALL_DURATION";

    String INTENT_DETAILS = "INTENT_DETAILS";
    String INTENT_DATA = "INTENT_DATA";
    String INTENT_TITLE = "INTENT_TITLE";
    String INTENT_IMAGE = "INTENT_IMAGE";
    String INTENT_UI = "INTENT_UI";
    String INTENT_URL = "INTENT_URL";
    String INTENT_KEY_MSISDN = "MSISDN";
    String INTENT_KEY_NAME = "NAME";
    String INTENT_KEY_EN = "EN";
    String INTENT_KEY_BN = "BN";
    String INTENT_KEY_LAB_TEST = "LAB_TEST";

    String UI_NOTIFICATION = "UI_NOTIFICATION";
    String UI_ORDER_MEDICINE = "UI_ORDER_MEDICINE";
    String UI_BUY_PACK = "UI_BUY_PACK";

    int RESULT_CODE_BUY_PACK = 101;



    */
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
    /*


    //=======================================================
    // DEEP_LINK-------------------------------------
    String DEEP_LINK_PAYMENT = "/payment";
    String DEEP_LINK_MY_HEALTH = "/my-health";
    String DEEP_LINK_buy_medicine = "/buy_medicine";
    String DEEP_LINK_call_ambulance = "/call_ambulance";
    String DEEP_LINK_call_nurse = "/call_nurse";
    String DEEP_LINK_call_physiotherapy = "/call_physiotherapy";

    //=======================================================

    boolean isDebugTestingStatus = false;
    boolean subStatusDebug = false;


    String loading_signing = "Signing..";
    String loading_doctors_find = "Doctors finding..";
    String Searching_doctor = "Searching Doctors";
    String DELETE_LOADING = "Deleting..";
    String UPLOADING = "Uploading..";
    String FETCHING_PRESCRIPTION = "Fetching Prescription Information";
    String Calling_to = "Calling to ";
    String Patient_in_call = "Can't place a new call while you're already in a call.";
    String CHANNEL = "android";

    String ERROR_INTERNET_CONNECTION = "Internet not available! Please connect to the Internet and try again!";
    String ACTIVE = "ACTIVE";
    String MEDICATION = "CURRENT_MEDICATION";
    String PREVIOUS_SURGERY = "PREVIOUS_SURGERY";
    String LANGUAGE_EN = "EN";
    String LANGUAGE_BN = "BN";
    String TEXT_MEDICINE = "Prescribe Medicine";
    String TEXT_MEDICINE_BN = "প্রেসক্রিপশনের ওষুধ";

    String TEXT_PRIVACY = "<p>I have read and agree to the <span style=\"color: #017BE6;\"><a href=\"https://medico.bio/terms-conditions\">Terms Conditions</a>, <a href=\"https://medico.bio/privacyPolicy\">Privacy Policy</a></span> and <span style=\"color: #017BE6;\"> <a href=\"https://medico.bio/privacyPolicy\">Return/refund-policy</a></span></p>";
    String TEXT_PRIVACY_BN = "<p>আমি পড়েছি এবং এর সাথে একমত <span style=\"color: #008080;\"><a href=\"https://medico.bio/terms-conditions\">শর্তাবলী</a>, <a href=\"https://medico.bio/privacyPolicy\">গোপনীয়তা নীতি </a></span> এবং <span style=\"color: #008080;\"><a href=\"https://medico.bio/privacyPolicy\">রিটার্ন/ফেরত নীতি</a></span></p>";

    String IVR_AMBULANCE_NUMBER = "+8801847050796";
    String IVR_NURSING_NUMBER = "+8801844476999";
    String IVR_Physio_NUMBER = "+8801844476988";
    String LONG_CODE_NUMBER_HELPLINE = "09613300333";


    String ORDER_TYPE_PRESCRIPTION = "ORDER_TYPE_PRESCRIPTION";
    String ORDER_TYPE_MEDICINE = "ORDER_TYPE_MEDICINE";
    String ORDER_TYPE_MEDICO_PRESCRIPTION = "ORDER_TYPE_MEDICO_PRESCRIPTION";


    String TYPE_ADVICE = "TYPE_ADVICE";
    String TYPE_ASSESSMENT = "TYPE_ASSESSMENT";
    String TYPE_INVESTIGATION = "TYPE_INVESTIGATION";
    String TEXT_ADVICE = "Advice";
    String TEXT_ASSESSMENT = "Assessment";
    String TEXT_INVESTIGATION = "Investigation";
    //=========================================================
    long TIMER_NOT_FOUND_DOCTOR_API = 3 * 1000;
    long TIMER_RECALL_DOCTOR_API = 5 * 1000;
    long TIMER_CONNECTING_TIME = 200 * 1000;
    long TIMER_RINGING_TIME = 150 * 1000;
    long TIMER_PRESENCES_CHECK_TIME = 23 * 1000;

    String AUTO_RENEWAL = "auto-renewal";
    String USER_PATIENT = "patient";

    String USER_DOCTOR = "DOCTOR";
    String OTP_RESEND = "OTP_RESEND";
    String FORGOT = "forgot";
    String NEW_USER = "newUser";

    String LAB_REPORT = "labReport";
    String PATH_PRESCRIPTION_ATTACHMENT = "prescriptionAttachment";
    String PATIENT_FOLDER_ORDER_PRESCRIPTION = "orderPrescription";

    String REMINDER_MODEL = "REMINDER";
    String REMINDER_ID = "REMINDER_ID";
    String MSISDN = "msisdn";
    String ADDITIONAL = "additional";

    String PUSH_KEY_APP_UPDATE = "app_update";
    String PUSH_KEY_PRESCRIPTION = "prescription";
    String PUSH_KEY_CHAT_MESSAGE = "chat_message";
    String PUSH_KEY_Logout = "logout";
    String PUSH_KEY_APP_PROMOTION = "app_promotion";
    String PUSH_KEY_payment = "payment";

    //=========================================================

    //===========firebase event============================
    String Event_Ambulance = "EVENT_AMBULANCE_CALL_CLICK";
    String EVENT_LOGIN_SCREEN_VIEW = "EVENT_LOGIN_SCREEN_VIEW";
    String EVENT_SIGNUP_SCREEN_VIEW = "EVENT_SIGNUP_SCREEN_VIEW";
    String EVENT_OTP_SCREEN_VIEW = "EVENT_OTP_SCREEN_VIEW";
    String EVENT_FORGET_SCREEN_VIEW = "EVENT_FORGET_SCREEN_VIEW";
    String EVENT_SET_NEW_PASSWORD_SCREEN_VIEW = "EVENT_SET_NEW_PASSWORD_SCREEN_VIEW";
    String EVENT_HOME_SCREEN_VIEW = "EVENT_HOME_SCREEN_VIEW";
    String EVENT_PAYMENT_SCREEN_VIEW = "EVENT_PAYMENT_SCREEN_VIEW";
    String EVENT_MY_HEALTH_SCREEN_VIEW = "EVENT_MY_HEALTH_SCREEN_VIEW";
    String EVENT_MY_DETAILS_SCREEN_VIEW = "EVENT_MY_DETAILS_SCREEN_VIEW";
    String EVENT_PREVIOUS_PRESCRIPTION_SCREEN_VIEW = "EVENT_PREVIOUS_PRESCRIPTION_SCREEN_VIEW";
    String EVENT_PREVIOUS_SURGERY_SCREEN_VIEW = "EVENT_PREVIOUS_SURGERY_SCREEN_VIEW";
    String EVENT_RUNNING_MEDICINE_SCREEN_VIEW = "EVENT_RUNNING_MEDICINE_SCREEN_VIEW";
    String EVENT_PRESCRIPTION_LIST_SCREEN_VIEW = "EVENT_PRESCRIPTION_LIST_SCREEN_VIEW";
    String EVENT_ORDER_HISTORY_SCREEN_VIEW = "EVENT_ORDER_HISTORY_SCREEN_VIEW";
    String EVENT_CALL_HISTORY_SCREEN_VIEW = "EVENT_CALL_HISTORY_SCREEN_VIEW";
    String EVENT_MEDICINE_REMINDER_SCREEN_VIEW = "EVENT_MEDICINE_REMINDER_SCREEN_VIEW";
    String EVENT_ADD_MEDICINE_REMINDER_SCREEN_VIEW = "EVENT_ADD_MEDICINE_REMINDER_SCREEN_VIEW";
    String EVENT_MEDICINE_ORDER_SCREEN_VIEW = "EVENT_MEDICINE_ORDER_SCREEN_VIEW";
    String EVENT_INVESTIGATION_ORDER_SCREEN_VIEW = "EVENT_INVESTIGATION_ORDER_SCREEN_VIEW";
    String EVENT_PRESCRIPTION_SCREEN_VIEW = "EVENT_PRESCRIPTION_SCREEN_VIEW";
    String EVENT_PACKAGE_LIST_SCREEN_VIEW = "EVENT_PRESCRIPTION_SCREEN_VIEW";
    String EVENT_CALL_SCREEN_VIEW = "EVENT_CALL_SCREEN_VIEW";
    String EVENT_CHAT_SCREEN_VIEW = "EVENT_CHAT_SCREEN_VIEW";
    String EVENT_PAYMENT_GATEWAY_SCREEN_VIEW = "EVENT_PAYMENT_GATEWAY_SCREEN_VIEW";
    String EVENT_LAB_REPORT_SCREEN_VIEW = "EVENT_LAB_REPORT_SCREEN_VIEW";

    String EVENT_LOGIN_CLICK = "EVENT_LOGIN_CLICK";
    String EVENT_SIGNUP_CLICK = "EVENT_SIGNUP_CLICK";
    String EVENT_FORGET_PASSWORD_CLICK = "EVENT_FORGET_PASSWORD_CLICK";
    String EVENT_OTP_VERIFY_CLICK = "EVENT_OTP_VERIFY_CLICK";
    String EVENT_RESET_PASSWORD_CLICK = "EVENT_RESET_PASSWORD_CLICK";
    String EVENT_CALL_CLICK = "EVENT_CALL_CLICK";
    String EVENT_CALL_CONNECTED = "EVENT_CALL_CONNECTED";
    String EVENT_CALL_NO_ANSWER = "EVENT_CALL_NO_ANSWER";
    String EVENT_CALL_END_CLICK = "EVENT_CALL_END_CLICK";
    String EVENT_CALL_HANGUP_CLICK = "EVENT_CALL_HANGUP_CLICK";
    String EVENT_SEND_CHAT_CLICK = "EVENT_SEND_CHAT_CLICK";
    String EVENT_PACKAGE_CLICK = "EVENT_PACKAGE_CLICK";
    String EVENT_GATEWAY_CLICK = "EVENT_GATEWAY_CLICK";
    String EVENT_MEDICINE_ORDER_CLICK = "EVENT_MEDICINE_ORDER_CLICK";
    String EVENT_MEDICINE_IMAGE_PRESCRIPTION_ORDER_CLICK = "EVENT_MEDICINE_IMAGE_PRESCRIPTION_ORDER_CLICK";
    String EVENT_MEDICINE_MEDICO_PRESCRIPTION_ORDER_CLICK = "EVENT_MEDICINE_MEDICO_PRESCRIPTION_ORDER_CLICK";
    String EVENT_INVESTIGATION_ORDER_CLICK = "EVENT_INVESTIGATION_ORDER_CLICK";
    String EVENT_PUSH_NOTIFICATION = "PUSH_NOTIFICATION_UI_OPEN";
    String EVENT_FORCE_UPDATE_DIALOG_OPEN = "EVENT_FORCE_UPDATE_DIALOG_OPEN";


    String EVENT_NURSING_CALL_CLICK = "EVENT_NURSING_CALL_CLICK";
    String EVENT_PHYSIO_CALL_CLICK = "EVENT_PHYSIO_CALL_CLICK";
    String EVENT_LANGUAGE_CHANGE = "EVENT_LANGUAGE_CHANGE";

    String EVENT_CLICK_UNSUBSCRIPTION = "EVENT_CLICK_UNSUBSCRIPTION";
    String EVENT_SUCCESS_UNSUBSCRIPTION = "EVENT_SUCCESS_UNSUBSCRIPTION";
    String EVENT_FAILED_UNSUBSCRIPTION = "EVENT_FAILED_UNSUBSCRIPTION";

    String VIDEO_CALL = "VIDEO_CALL";
    String AUDIO_CALL = "AUDIO_CALL";
*/


}
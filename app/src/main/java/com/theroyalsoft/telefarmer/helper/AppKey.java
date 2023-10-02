package com.theroyalsoft.telefarmer.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.farmer.primary.network.utils.NullRemoveUtil;
import com.theroyalsoft.telefarmer.BuildConfig;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public interface AppKey {


    String INTENT_FROM_PRESCRIPTION_NOTIFICATION = "INTENT_FROM_PRESCRIPTION_NOTIFICATION";
    String INTENT_PRESCRIPTION_ID = "INTENT_PRESCRIPTION_ID";
    String INTENT_BILLING_URL = "INTENT_BILLING_URL";
    String INTENT_VIDEO_CALL = "INTENT_VIDEO_CALL";
    String INTENT_DETAILS = "INTENT_DETAILS";
    String INTENT_DATA = "INTENT_DATA";
    String INTENT_TITLE = "INTENT_TITLE";
    String INTENT_IMAGE = "INTENT_IMAGE";
    String INTENT_UI = "INTENT_UI";
    String INTENT_KEY_MSISDN = "MSISDN";
    String INTENT_KEY_NAME = "NAME";
    String INTENT_KEY_EN = "EN";
    String INTENT_KEY_BN = "BN";
    String INTENT_KEY_LAB_TEST = "LAB_TEST";

    String UI_NOTIFICATION = "UI_NOTIFICATION";
    String UI_ORDER_MEDICINE = "UI_ORDER_MEDICINE";


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
    String FETCHING_PRESCRIPTION = "e-Prescription Loading..";
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


//    static String getLogSubStatus() {
//        return "SUB_STATUS:" + LocalData.getSubStatus();
//    }


    //=============================================================

    static boolean isDebugTesting() {
        if (BuildConfig.DEBUG) {
            return isDebugTestingStatus;
        } else {
            return false;
        }
    }

    //2022-10-12T12:11:17.000Z
    String DATE_TIME_FORmMATE_0 = "yyyy-MM-dd HH:mm:ss";
    String DATE_TIME_FORmMATE_1 = "yyyy-MM-dd'T'hh:mm:ssZ";
    String DATE_TIME_FORmMATE_6 = "yyyy-MM-dd'T'hh:mm:ssZ.ffff";
    String DATE_TIME_FORmMATE_2 = "dd MMM yy";
    String DATE_TIME_FORmMATE_3 = "yyyy/MM/dd HH:mm:ss";
    // String DATE_TIME_FORmMATE_4 = "dd MMM YYYY";
    String DATE_TIME_FORmMATE_4 = "yyyy/MM/dd";
    String DATE_TIME_FORmMATE_5 = "dd/MM/yyyy";
    String TIME_FORMAT = "hh:mm aaa";
    String REMINDER_DATE_TIME_FORMAT4 = "dd MMM yyyy";
    String REMINDER_DATE_TIME_FORMAT = "dd MMM yy hh:mm a";
    String REMINDER_DATE_TIME_FORMAT1 = "dd MMM yy hh:mma";

    static String getTime1(String time, String formatterType, String formatterType1) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);
        Format formatter = new SimpleDateFormat(formatterType1);

        Date date = new Date();
        try {
            date = dateFormat.parse(time);
            String time1 = formatter.format(date);
            // Timber.d("Date is: " + time1);
            return NullRemoveUtil.getNotNull(time1);

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return time;
    }

    static String getTimeForDob(String time, String fromType, String toType) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(fromType);
        Format formatter = new SimpleDateFormat(toType);

        Date date = new Date();
        try {
            date = dateFormat.parse(time);
            String time1 = formatter.format(date);
            Timber.d("Date is: " + time1);
            return NullRemoveUtil.getNotNull(time1);

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return "";
    }


    static String getTime1(long timeLong) {
        String time = "";
        Format formatter = new SimpleDateFormat(AppKey.DATE_TIME_FORmMATE_0);

        Date date = new Date();
        date.setTime(timeLong);
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }

    static String getTime1() {
        String time = "";
        Format formatter = new SimpleDateFormat(AppKey.DATE_TIME_FORmMATE_0);

        Date date = new Date();
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }

    static String getTimeCurrent() {
        String time = "";
        Format formatter = new SimpleDateFormat(AppKey.TIME_FORMAT);

        Date date = new Date();
        String time1 = formatter.format(date);
        Timber.d("Date is: " + time1);
        return NullRemoveUtil.getNotNull(time1);

    }


    static Date getTime(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static Date getTimeForReminder(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static Date getTime3(String dateTime, String formatterType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatterType);

        Date date = new Date();
        try {
            date = dateFormat.parse(dateTime);
            return date;

        } catch (ParseException e) {
            Timber.e("Error:" + e.toString());
        }
        return date;

    }

    static String getTime4(String formated) {
        DateFormat dform = new SimpleDateFormat(formated);
        Date obj = new Date();
        return dform.format(obj);

    }


    //=============================================
    static String getSecond(long millis) {
        int seconds = (int) (millis / 1000);
        int time = seconds % 60;
        return time > 9 ? time + "" : "0" + time;
    }

    static String getMinute(long millis) {
        int seconds = (int) (millis / 1000);
        int time = seconds / 60;
        return time > 9 ? time + "" : "0" + time;
    }

    static String getCallDuration(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        String secondsText = seconds > 9 ? seconds + "" : "0" + seconds;
        String minutesText = minutes > 9 ? minutes + "" : "0" + minutes;
        String hoursText = hours > 0 ? hours + "" : "";

        int time = seconds / 60;
        return time > 9 ? time + "" : "0" + time;
    }
    //=============================================


    static int getReminderID() {
        return (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }


    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);

    static void setTextWithFadeInFadOutAnim(TextView txtView, String text, boolean isAnimation) {
        Timber.e("text:" + text);
        if (!isAnimation) {
            txtView.setText(text);
            return;
        }


        txtView.startAnimation(fadeIn);
        txtView.startAnimation(fadeOut);

        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
        txtView.setText(text);

        fadeOut.setDuration(1200);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(4200 + fadeIn.getStartOffset());

    }


    static void goDialer(Activity activity, String mobileNumber) {
        Intent intent1 = new Intent(Intent.ACTION_DIAL);
        intent1.setData(Uri.parse("tel:" + mobileNumber));
        activity.startActivity(intent1);
    }
}

package bio.medico.patient.firebaseManager;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

import bio.medico.patient.apputil.AppUtilConfig;
import bio.medico.patient.common.AppKey;
import bio.medico.patient.data.local.LocalData;
import timber.log.Timber;

public class FirebaseAnalyticsManager2 {

    static FirebaseAnalytics mFirebaseAnalytics;

    public static void init() {
        if (mFirebaseAnalytics == null) {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(AppUtilConfig.getAppContext());
            Timber.d("FirebaseAnalytics new Create!");
        }
    }

    public static void logEvent(String eventName) {
        Bundle params = new Bundle();
        params.putString(AppKey.CHANNEL, AppKey.USER_PATIENT);
        mFirebaseAnalytics.logEvent(eventName, params);
        Timber.e("Event Triggered:: " + eventName);
    }


    public static void logEventAmbulanceCall() {
        logEvent(AppKey.Event_Ambulance);
    }


    public static void logEventWithNumber(String eventName, String additional, String msisdn) {
        try {
            Bundle params = new Bundle();
            params.putString(AppKey.CHANNEL, AppKey.USER_PATIENT);
            params.putString(AppKey.ADDITIONAL, additional);
            params.putString(AppKey.MSISDN, msisdn);
            mFirebaseAnalytics.logEvent(eventName, params);
            Timber.e("Event Triggered:: " + eventName);
        } catch (Exception e) {
            Timber.e("Error: " + e.toString());
        }
    }


    public static void logEventWithNumber(String event) {
        logEventWithNumber(event, "", Objects.requireNonNull(LocalData.getPhoneNumber()));
    }

    public static void logEventWithNumber(String event, String params) {
        logEventWithNumber(event, params, Objects.requireNonNull(LocalData.getPhoneNumber()));
    }

    public static void logEventWithoutNumber(String event, String params) {
        logEventWithNumber(event, params, "");
    }

    public static void logEventWithoutNumber(String event) {
        logEventWithNumber(event, "", "");
    }

}

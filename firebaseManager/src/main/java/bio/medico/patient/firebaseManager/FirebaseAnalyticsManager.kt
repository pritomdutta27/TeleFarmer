package bio.medico.patient.firebaseManager

import android.os.Bundle
import bio.medico.patient.apputil.AppUtilConfig
import bio.medico.patient.common.AppKey
import bio.medico.patient.data.local.LocalData
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber
import java.util.Objects

class FirebaseAnalyticsManager {
    companion object {

        private var mFirebaseAnalytics: FirebaseAnalytics? = null

        @JvmStatic
        fun init() {
            if (mFirebaseAnalytics == null) {
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(AppUtilConfig.getAppContext())
                Timber.d("FirebaseAnalytics new Create!")
            }
        }

        @JvmStatic
        fun logEvent(eventName: String) {
            val params = Bundle()
            params.putString(AppKey.CHANNEL, AppKey.USER_PATIENT)
            mFirebaseAnalytics!!.logEvent(eventName, params)
            Timber.e("Event Triggered:: $eventName")
        }

        @JvmStatic
        fun logEventAmbulanceCall() {
            logEvent(AppKey.Event_Ambulance)
        }

        @JvmStatic
        fun logEventWithNumber(eventName: String, additional: String?, msisdn: String?) {
            try {
                val params = Bundle()
                params.putString(AppKey.CHANNEL, AppKey.USER_PATIENT)
                params.putString(AppKey.ADDITIONAL, additional)
                params.putString(AppKey.MSISDN, msisdn)
                mFirebaseAnalytics!!.logEvent(eventName, params)
                Timber.e("Event Triggered:: $eventName")
            } catch (e: Exception) {
                Timber.e("Error: $e")
            }
        }

        @JvmStatic
        fun logEventWithNumber(event: String) {
            logEventWithNumber(event, "", Objects.requireNonNull(LocalData.getPhoneNumber()))
        }

        @JvmStatic
        fun logEventWithNumber(event: String, params: String?) {
            logEventWithNumber(event, params, Objects.requireNonNull(LocalData.getPhoneNumber()))
        }

        fun logEventWithoutNumber(event: String, params: String?) {
            logEventWithNumber(event, params, "")
        }

        @JvmStatic
        fun logEventWithoutNumber(event: String) {
            logEventWithNumber(event, "", "")
        }
    }
}
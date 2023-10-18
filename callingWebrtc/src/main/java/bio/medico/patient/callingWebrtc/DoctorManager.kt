package bio.medico.patient.callingWebrtc

import android.os.Handler
import bio.medico.patient.common.AppKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber


/**
Created by Samiran Kumar on 26,September,2023
 **/
class DoctorManager {

    private var handlerPresencesCheckTime = Handler()
    private var handlerCalleeNotFound = Handler()
    private var handlerRecallDoctorTime = Handler()
    private var handlerRingingTime = Handler()
    private var handlerConnectingTime = Handler()

    fun startPresencesCheckTimer(retryFoundDoctor: () -> Unit) {
        Timber.i("Start handlerPresencesCheckTime Handler")

  /*   val job =   CoroutineScope(Dispatchers.IO).launch {
            delay(AppKey.TIMER_PRESENCES_CHECK_TIME)
            retryFoundDoctor.invoke()
        }*/



        closeHandlerPresencesCheckTime()
        handlerPresencesCheckTime.postDelayed({
            Timber.i("Run handlerPresencesCheckTime Handler")
            Timber.e("======reTryFoundDoctor====>> PresencesCheckTime out")

            retryFoundDoctor.invoke()
        }, AppKey.TIMER_PRESENCES_CHECK_TIME)
    }


    //=====================================================================
    //=====================================================================
    fun handlerCalleeNotFound(retryFoundDoctor: () -> Unit) {
        handlerCalleeNotFound.postDelayed({
            Timber.e("======reTryFoundDoctor====>> CALLEE_NOT_FOUND")
            retryFoundDoctor.invoke()
        }, AppKey.TIMER_NOT_FOUND_DOCTOR_API)
    }


    fun handlerRecallDoctorTime(retryFoundDoctor: () -> Unit) {
        handlerRecallDoctorTime.postDelayed({
            Timber.e("======reTryFoundDoctor====>> RecallDoctorTime End")
            retryFoundDoctor.invoke()
        }, AppKey.TIMER_RECALL_DOCTOR_API)
    }


    fun closeHandlerPresencesCheckTime() {
        Timber.i("handlerPresencesCheckTime closeHandler")
        handlerPresencesCheckTime.removeCallbacksAndMessages(null)
    }

    fun closeHandlerCalleeNotFound() {
        Timber.i("handlerCalleeNotFound closeHandler")
        handlerCalleeNotFound.removeCallbacksAndMessages(null)
    }

    fun closeHandlerRecallDoctorTime() {
        Timber.i("handlerRecallDoctorTime closeHandler")
        handlerRecallDoctorTime.removeCallbacksAndMessages(null)
    }

    fun closeHandlerRingingTime() {
        Timber.i("noAnswerCallHandler closeHandler")
        handlerRingingTime.removeCallbacksAndMessages(null)
    }

    fun closeHandlerConnectingTime() {
        Timber.i("handlerConnectingTime closeHandler")
        handlerConnectingTime.removeCallbacksAndMessages(null)
    }
}
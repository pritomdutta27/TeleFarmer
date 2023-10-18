package bio.medico.patient.callingWebrtc.freeCall

import android.app.Activity
import android.os.Handler
import bio.medico.patient.data.local.LocalData
import bio.medico.patient.model.apiResponse.ResponseSubInfo
import com.theroyalsoft.mydoc.apputil.TimeUtil
import timber.log.Timber
import java.util.Date


/**
Created by Samiran Kumar on 26,September,2023
 **/
class FreeCallManager(
    val activity: Activity,
    private val freeCallInfo: ResponseSubInfo.FreeCallInfo, private val timeOutFreeCall: () -> Unit
) {

    private var handlerFreeCallTime = Handler()

    private var freeCallWarningDialog: FreeCallWarningDialog? = null


    fun startFreeCallTimer() {
        Timber.i("Start startFreeCallTimer Handler")

        handlerFreeCallTime.postDelayed({
            Timber.e("End timer Run startFreeCallTimer Handler")

            Timber.i("closeFreeCallTimer")
            handlerFreeCallTime.removeCallbacksAndMessages(null)

            timeOutFreeCall.invoke()

        }, ((freeCallInfo.callDuration + 1) * 1000).toLong())

    }


    fun showFreeCallWarningDialog(dismissDialog: () -> Unit) {
        //  bitmap = blur(activity, bitmap);
        // bitmap = ImgBlur.applyBlur(activity, bitmap, 20);
        freeCallWarningDialog = FreeCallWarningDialog(activity) {

            freeCallWarningDialog!!.dismiss()
            dismissDialog.invoke()
        }

        freeCallWarningDialog!!.showDialog()

    }

    fun checkAvailable() {

        try {

            //local data
            val callLimitStatusExpire =
                LocalCallLimitStatusExpire.getCallLimitStatusExpire()
            if (callLimitStatusExpire == null) {
                val json = LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                    LocalCallLimitStatusExpire(
                        LocalData.getUserUuid(), TimeUtil.getToday(), 1
                    )
                )
                LocalData.setCallLimit(json)
            } else {
                if (freeCallInfo != null) {
                    when (freeCallInfo.callLimitType) {
                        LocalCallLimitStatusExpire.STATUS_TYPE_ALL_TIME -> {
                            callLimitStatusExpire.callCount =
                                callLimitStatusExpire.callCount + 1
                            callLimitStatusExpire.dateTime = TimeUtil.getToday()
                            val json =
                                LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                                    callLimitStatusExpire
                                )
                            LocalData.setCallLimit(json)
                        }

                        LocalCallLimitStatusExpire.STATUS_TYPE_EVERYDAY -> {
                            val sameDay = TimeUtil.isSameDay(
                                TimeUtil.getTime(
                                    callLimitStatusExpire.dateTime,
                                    TimeUtil.DATE_TIME_FORmMATE_4
                                ), Date()
                            )
                            if (sameDay) {
                                callLimitStatusExpire.callCount =
                                    callLimitStatusExpire.callCount + 1
                                callLimitStatusExpire.dateTime = TimeUtil.getToday()
                                val json1 =
                                    LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                                        callLimitStatusExpire
                                    )
                                LocalData.setCallLimit(json1)
                            } else {
                                val json2 =
                                    LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                                        LocalCallLimitStatusExpire(
                                            LocalData.getUserUuid(),
                                            TimeUtil.getToday(),
                                            1
                                        )
                                    )
                                LocalData.setCallLimit(json2)
                            }
                        }

                        else -> {
                            val sameDay = TimeUtil.isSameDay(
                                TimeUtil.getTime(
                                    callLimitStatusExpire.dateTime,
                                    TimeUtil.DATE_TIME_FORmMATE_4
                                ), Date()
                            )
                            if (sameDay) {
                                callLimitStatusExpire.callCount =
                                    callLimitStatusExpire.callCount + 1
                                callLimitStatusExpire.dateTime = TimeUtil.getToday()
                                val json1 =
                                    LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                                        callLimitStatusExpire
                                    )
                                LocalData.setCallLimit(json1)
                            } else {
                                val json2 =
                                    LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(
                                        LocalCallLimitStatusExpire(
                                            LocalData.getUserUuid(),
                                            TimeUtil.getToday(),
                                            1
                                        )
                                    )
                                LocalData.setCallLimit(json2)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e("Error:$e")
        }
    }

}


//===============================================================
fun ResponseSubInfo.FreeCallInfo.hasFreeCallAvailable(): Boolean {
    var isFreeCall = false

    //isEligibility
    if (!this.isEligibility) {
        Timber.d("isFreeCall:$isFreeCall | isEligibility")
        return isFreeCall
    }

    //isCallLimitStatus
    if (!this.isCallLimitStatus) {
        isFreeCall = true
        Timber.d("isFreeCall:$isFreeCall | isCallLimitStatus")
        return isFreeCall
    }


    //local data
    val callLimitStatusExpire = LocalCallLimitStatusExpire.getCallLimitStatusExpire()
    if (callLimitStatusExpire == null) {
        isFreeCall = true
        Timber.d("isFreeCall:$isFreeCall | callLimitStatusExpire")
        return isFreeCall
    }
    if (callLimitStatusExpire.getUserId() != LocalData.getUserUuid()) {
        isFreeCall = true
        Timber.d("isFreeCall:$isFreeCall | getUserUuid")
        return isFreeCall
    }
    val sameDay = TimeUtil.isSameDay(
        TimeUtil.getTime(
            callLimitStatusExpire.getDateTime(),
            TimeUtil.DATE_TIME_FORmMATE_4
        ), Date()
    )
    if (sameDay) {
        if (callLimitStatusExpire.getCallCount() < this.callLimit) {
            isFreeCall = true
            Timber.d("isFreeCall:$isFreeCall | getCallLimit")
        } else {
            isFreeCall = false
            Timber.d("isFreeCall:$isFreeCall | getCallLimit")
        }
    } else {
        isFreeCall = true
        Timber.d("isFreeCall:$isFreeCall | sameDay")
    }

    //-------------------------------------------
    Timber.e("isFreeCall:$isFreeCall")
    return isFreeCall
}

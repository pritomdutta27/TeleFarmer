package bio.medico.patient.data.activityLog

import bio.medico.patient.common.AppKeyLog
import bio.medico.patient.data.ApiManager
import bio.medico.patient.data.ApiManagerNew


/**
Created by Samiran Kumar on 12,September,2023
 **/
object AppLog {

    @JvmStatic
    fun sendUiOpen(uiType: String) {
        ApiManager.sendApiLogAppOpen(uiType)
    }

    @JvmStatic
    fun sendUiClose(uiType: String, message: String) {
        sendApiLog(uiType, AppKeyLog.UI_CLOSE, message)
    }

    @JvmStatic
    fun sendStatus(uiType: String, message: String) {
        sendApiLog(uiType, AppKeyLog.KEY_STATUS, message)
    }

    @JvmStatic
    fun sendButton(uiType: String, message: String) {
        sendApiLog(uiType, AppKeyLog.CLICK_BUTTON, message)
    }

    @JvmStatic
    fun sendApiCAll(
        uiType: String,
        userActivity: String = AppKeyLog.NA,
        endPointType: String = AppKeyLog.NA,
        endPoint: String = AppKeyLog.NA,
        message: String
    ) {
        ApiManager.sendApiLog(uiType, userActivity, endPointType, endPoint, message)
    }


    @JvmStatic
    fun sendApiLogErrorCodeScope(exception: Exception) {
        ApiManager.sendApiLogErrorCodeScope(exception)
    }


    @JvmStatic
    fun sendSocketLog(uiType: String, endPoint: String, message: String) {
        ApiManager.sendApiLog(
            uiType,
            AppKeyLog.SEND_SOCKET,
            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
            endPoint,
            message
        )
    }

    @JvmStatic
    fun sendSocketLog(uiType: String, userActivity: String, endPoint: String, message: String) {
        ApiManager.sendApiLog(
            uiType,
            userActivity,
            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
            endPoint,
            message
        )
    }

    @JvmStatic
    fun sendSocketConnection(
        uiType: String,
        endPoint: String,
        message: String
    ) {
        ApiManager.sendApiLogEndpointSocket(
            uiType,
            AppKeyLog.SEND_CONNECTION,
            endPoint,
            message
        )
    }


    //==================================================================================
    @JvmStatic
    fun sendApiLog(
        uiType: String,
        userActivity: String,
        message: String
    ) {

        ApiManagerNew.logApi(
            uiType,
            userActivity,
            AppKeyLog.NA,
            AppKeyLog.NA,
            message
        )
    }

    @JvmStatic
    fun sendApiLog(
        uiType: String,
        userActivity: String,
        endPointType: String,
        endPoint: String,
        message: String
    ) {
        ApiManagerNew.logApi(
            uiType,
            userActivity,
            endPointType,
            endPoint,
            message
        )
    }

}
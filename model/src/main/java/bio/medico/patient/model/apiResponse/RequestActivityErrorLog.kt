package bio.medico.patient.model.apiResponse

class RequestActivityErrorLog(
    private val msisdn: String,
    private val uuid: String,
    private val deviceId: String,
    private val appVersion: String,
    private val osVersion: String,
    private val deviceName: String,
    private val channel: String,
    private val uiType: String,
    private val errorType: String,
    private val userActivity: String,
    private val endPointType: String,
    private val endPoint: String,
    private val message: String
)
package bio.medico.patient.model.apiResponse

class RequestOtpSend(
    private val channel: String,
    private val deviceId: String,
    private val phoneNumber: String,
    private val userType: String,
    private val type: String
)
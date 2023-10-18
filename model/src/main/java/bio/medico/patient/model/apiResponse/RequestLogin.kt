package bio.medico.patient.model.apiResponse

class RequestLogin(
    private val userType: String,
    private val channel: String,
    private val deviceId: String,
    private val phoneNumber: String,
    private val type: String
)
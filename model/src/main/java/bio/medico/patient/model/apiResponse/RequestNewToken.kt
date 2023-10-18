package bio.medico.patient.model.apiResponse

class RequestNewToken(
    private val accessToken: String,
    private val phoneNumber: String,
    private val uuid: String,
    private val channel: String
)
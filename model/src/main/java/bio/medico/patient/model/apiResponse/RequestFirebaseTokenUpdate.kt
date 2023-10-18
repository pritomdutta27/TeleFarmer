package bio.medico.patient.model.apiResponse

class RequestFirebaseTokenUpdate(
    private val isLogin: Boolean,
    private val firebaseToken: String,
    private val deviceId: String,
    private val userType: String,
    private val status: String,
    private val channel: String,
    private val phoneNumber: String,
    private val userUid: String,
    private val name: String
)
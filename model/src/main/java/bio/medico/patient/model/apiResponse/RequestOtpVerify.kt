package bio.medico.patient.model.apiResponse

class RequestOtpVerify(
    var phoneNumber: String,
    var otp: String,
    var deviceId: String,
    var isTrusted: Boolean,
    var deviceName: String,
    var channel: String
)
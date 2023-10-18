package bio.medico.patient.model.apiResponse

class RequestSignUp(
    var name: String,
    var phoneNumber: String,
    var password: String,
    var confirmPassword: String,
    var channel: String,
    var iosDeviceId: String,
    var androidDeviceId: String,
    var dob: String
)
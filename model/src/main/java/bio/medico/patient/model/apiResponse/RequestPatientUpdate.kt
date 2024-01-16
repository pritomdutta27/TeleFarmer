package bio.medico.patient.model.apiResponse

class RequestPatientUpdate(
    private var location: String = "",
    private var height: String? = "",
    private var weight: String? = "",
    private var dob: String = "",
    val name: String,
    private var gender: String? = "",
    var image: String? = ""
)
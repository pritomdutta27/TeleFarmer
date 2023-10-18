package bio.medico.patient.model.apiResponse

class RequestPatientUpdate(
    private val location: String,
    private val height: String,
    private val weight: String,
    private val dob: String,
    val name: String,
    private val gender: String,
    private val image: String
)
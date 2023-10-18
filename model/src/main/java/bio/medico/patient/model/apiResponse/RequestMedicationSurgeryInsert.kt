package bio.medico.patient.model.apiResponse

class RequestMedicationSurgeryInsert(
    private val type: String,
    private val status: String,
    private val channel: String,
    private val body: String,
    private val deviceId: String,
    private val patientUuid: String
)
package bio.medico.patient.model.socket

data class PreOfferSocket(
    private val patientName: String,
    private val patientImage: String,
    private val dateAndTime: String,
    private val callType: String,
    private val isDemoCall: Boolean,
    private val fromId: String,
    private val toId: String,
    private val fromDeviceId: String,
    private val toDeviceId: String,
    private val channel: String
)
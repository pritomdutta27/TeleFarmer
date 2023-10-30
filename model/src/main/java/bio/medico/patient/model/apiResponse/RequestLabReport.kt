package bio.medico.patient.model.apiResponse

class RequestLabReport {
    private var channel: String
    private var patientUuid: String
    private var fileUrl: String
    private var patientName: String? = null
    private var phoneNumber: String? = null


    constructor(patientUuid: String, fileUrl: String, channel: String) {
        this.patientUuid = patientUuid
        this.fileUrl = fileUrl
        this.channel = channel
    }

    constructor(
        patientUuid: String,
        fileUrl: String,
        channel: String,
        patientName: String?,
        phoneNumber: String?
    ) {
        this.patientUuid = patientUuid
        this.fileUrl = fileUrl
        this.channel = channel
        this.patientName = patientName
        this.phoneNumber = phoneNumber
    }
}
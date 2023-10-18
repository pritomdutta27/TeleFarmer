package bio.medico.patient.model.apiResponse

data class ResponseMedication(
    var items: List<Item>? = null
) {
    inner class Item(
        var id: String? = null,
        var rev: String? = null,
        var patientUuid: String? = null,
        var deviceId: String? = null,
        var body: String? = null,
        var channel: String? = null,
        var status: String? = null,
        var type: String? = null
    )
}
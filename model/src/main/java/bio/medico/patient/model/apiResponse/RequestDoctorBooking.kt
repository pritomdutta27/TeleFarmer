package bio.medico.patient.model.apiResponse


/**
Created by Samiran Kumar on 17,May,2023
 **/
data class RequestDoctorBooking(
    val doctorId: String,
    val doctorImage: String,
    val doctorName: String,
    val patientId: String,
    val patientImage: String,
    val patientName: String,
    val scheduleId: String
)
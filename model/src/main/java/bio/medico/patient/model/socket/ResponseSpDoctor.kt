package bio.medico.patient.model.socket


/**
Created by Samiran Kumar on 16,August,2023
 **/
data class ResponseSpDoctor(
    var patientId: String,
    var doctorId: String,
    var roomName: String,
    var jwt: String,
    var status: SpDoctorCallStatus,
)


//    "status": 'removed' | 'addToCall'
enum class SpDoctorCallStatus { removed, addToCall, endCall }
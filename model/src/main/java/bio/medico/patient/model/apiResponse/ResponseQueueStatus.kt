package bio.medico.patient.model.apiResponse


/**
Created by Samiran Kumar on 11,September,2023
 **/
data class ResponseQueueStatus(
    val jitsiAdminRoom: String,
    val jitsiAdminToken: String,
    val jitsiDoctorRoom: String,
    val jitsiDoctorToken: String,
    val status: QueueStatus?
) {
    var specializedDoctor: SpecializedDoctor? = null
}


enum class QueueStatus {
    calling, waiting, dropped, cs_query, save_draft, completed, pending
}

/*
queueStatus : {
    CALLING : 'calling',
    WAITING : 'waiting',
    DROPPED : 'dropped',
    CS_QUERY : 'cs_query',
    SAVE_DRAFT : 'save_draft',
    COMPLETED : 'completed',
    PENDING : 'pending'  //for first time join in waiting room
}
* */

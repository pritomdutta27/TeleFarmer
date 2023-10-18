package bio.medico.patient.model.socket

/**
Created by Samiran Kumar on 10,August,2023
 **/
data class SignalingOfferSocket(
    var type: String,
    var offer: String,
    var answer: String,
    var candidate: String,
    var fromId: String,
    var toId: String,
    var toDeviceId: String,
    var fromDeviceId: String,
    var channel: String
)
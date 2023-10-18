package bio.medico.patient.model.socket

/**
Created by Samiran Kumar on 10,August,2023
 **/
data class UserInfoSocket(
    var uuid: String,
    var socketId: String,
    var userName: String,
    var userMobile: String,
    var dateAndTime: String,
    var userType: String,
    var deviceId: String,
    var channel: String
)
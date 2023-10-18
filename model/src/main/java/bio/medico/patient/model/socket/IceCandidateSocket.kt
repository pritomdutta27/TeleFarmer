package bio.medico.patient.model.socket

import bio.medico.patient.model.NullRemoveUtil

data class IceCandidateSocket(
    private val sdp: String,
    val sDPMLineIndex: Int, //sdpMid
    private val sdpMid: String
) {
    val sDP: String
        get() = NullRemoveUtil.getNotNull(sdp)
    val sDPMid: String
        get() = NullRemoveUtil.getNotNull(sdpMid)
}
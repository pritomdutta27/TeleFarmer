package bio.medico.patient.model.socket

import bio.medico.patient.model.NullRemoveUtil

data class PreOfferAnswerSocket(
    private val preOfferAnswer: String,
    private val fromId: String,
    private val toId: String,
    private val fromDeviceId: String,
    private val toDeviceId: String,
    private val channel: String
) {
    fun getFromDeviceId(): String {
        return NullRemoveUtil.getNotNull(fromDeviceId)
    }

    fun getPreOfferAnswer(): String {
        return NullRemoveUtil.getNotNull(preOfferAnswer)
    }
}
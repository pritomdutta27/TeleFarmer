package bio.medico.patient.common.ui


/**
Created by Samiran Kumar on 11,September,2023
 **/
sealed class UiStatus
class InstructionUi(val url: String, val message: String) : UiStatus()
class BlockUi(val message: String) : UiStatus()
class ShowMessage(val message: String) : UiStatus()
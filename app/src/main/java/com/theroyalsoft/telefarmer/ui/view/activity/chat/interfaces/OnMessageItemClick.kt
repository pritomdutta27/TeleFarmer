package com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces

import bio.medico.patient.model.apiResponse.chat.MessageBody


interface OnMessageItemClick {
    fun onMessageClick(message: MessageBody, position: Int)
    fun onMessageLongClick(message: MessageBody, position: Int)
}
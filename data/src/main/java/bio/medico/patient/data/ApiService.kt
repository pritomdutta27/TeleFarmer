package bio.medico.patient.data

import bio.medico.patient.model.apiResponse.ResponseChatList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //get message
    @GET(AppUrl.URL_MESSAGES)
    suspend fun getMessage(
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
    ): ResponseChatList


    @GET("chat-messge/user/chatlist")
    suspend fun getChatList(
        @Query("page_number") page_number: Int,
        @Query("per_page_count") page: Int
    ): ResponseChatList

    //=============================================================
}
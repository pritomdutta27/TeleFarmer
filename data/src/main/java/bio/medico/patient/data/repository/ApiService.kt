package bio.medico.patient.data.repository

import bio.medico.patient.data.AppUrl
import bio.medico.patient.model.apiResponse.RequestDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseError
import bio.medico.patient.model.apiResponse.ResponseQueueStatus
import bio.medico.patient.model.apiResponse.ResponseSpecialized
import bio.medico.patient.model.apiResponse.ResponseSpecializedDoctorStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
Created by Samiran Kumar on 25,July,2023
 **/
interface ApiService {

    @GET(AppUrl.URL_SPECIALIST_DOCTOR_STATUS)
    suspend fun getSpecialistDoctorStatus(): NetworkResponse<ResponseSpecializedDoctorStatus, ResponseError>

    @GET(AppUrl.URL_SPECIALIST_DOCTOR_SCHEDULE_LIST)
    suspend fun getSpecialistDoctorSchedule(
        @Path(AppUrl.user_id) userId: String
    ): NetworkResponse<ResponseSpecialized, ResponseError>


    @GET(AppUrl.URL_SPECIALIST_DOCTOR_BOOKED_SCHEDULE)
    suspend fun getSpecialistDoctorBookedSchedule(
        @Path(AppUrl.user_id) userId: String
    ): NetworkResponse<ResponseSpecialized, ResponseError>

    @POST(AppUrl.URL_SPECIALIST_DOCTOR_BOOKING)
    suspend fun setDoctorBooking(
        @Body requestDoctorBooking: RequestDoctorBooking
    ): NetworkResponse<ResponseDoctorBooking, ResponseError>


    @GET(AppUrl.URL_SPECIALIST_DOCTOR_QUEUE_LIST)
    suspend fun getSpecialistDoctorQueueList(
        @Path(AppUrl.doctorId) doctorId: String,
        @Path(AppUrl.patientId) patientId: String,
        @Path(AppUrl.scheduleId) scheduleId: String
    ): NetworkResponse<String, ResponseError>

    @POST(AppUrl.URL_SPECIALIST_DOCTOR_QUEUE_CALLING_STATUS_UPDATE)
    suspend fun setSpecialistDoctorQueueCallingStatusUpdate(
        @Path(AppUrl.patientId) doctorId: String,
        @Path(AppUrl.doctorId) patientId: String,
        @Path(AppUrl.scheduleId) scheduleId: String,
        @Path(AppUrl.queueStatus) queueStatus: String
    ): NetworkResponse<String, ResponseError>

    @POST(AppUrl.URL_SPECIALIST_DOCTOR_BOOKING_CANCEL)
    suspend fun cancelBookedSpecialistDoctor(
        @Path(AppUrl.scheduleId) scheduleId: String,
        @Path(AppUrl.patientId) patientId: String
    ): NetworkResponse<ResponseError, ResponseError>

    @GET(AppUrl.URL_SPECIALIST_DOCTOR_QUEUE_STATUS_BY_PATIENT)
    suspend fun getQueueStatusByPatient(
        @Path(AppUrl.patientId) patientId: String,
        @Path(AppUrl.scheduleId) scheduleId: String
    ): NetworkResponse<ResponseQueueStatus, ResponseError>


}
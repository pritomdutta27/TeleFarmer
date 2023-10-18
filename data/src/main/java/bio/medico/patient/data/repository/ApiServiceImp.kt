package bio.medico.patient.data.repository

import bio.medico.patient.model.apiResponse.RequestDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseError
import bio.medico.patient.model.apiResponse.ResponseQueueStatus
import bio.medico.patient.model.apiResponse.ResponseSpecialized

class ApiServiceImp(private val apiServices: ApiService) {

    suspend fun getSpecialistDoctorBookedSchedule(
        userId: String
    ): NetworkResponse<ResponseSpecialized, ResponseError> =
        apiServices.getSpecialistDoctorBookedSchedule(userId)

    suspend fun getSpecialistDoctorSchedule(
        userId: String
    ): NetworkResponse<ResponseSpecialized, ResponseError> =
        apiServices.getSpecialistDoctorSchedule(userId)


    suspend fun setDoctorBooking(
        requestDoctorBooking: RequestDoctorBooking
    ): NetworkResponse<ResponseDoctorBooking, ResponseError> =
        apiServices.setDoctorBooking(requestDoctorBooking)


    suspend fun getSpecialistDoctorQueueList(
        doctorId: String,
        patientId: String,
        scheduleId: String
    ): NetworkResponse<String, ResponseError> =
        apiServices.getSpecialistDoctorQueueList(doctorId, patientId, scheduleId)

    suspend fun setSpecialistDoctorQueueCallingStatusUpdate(
        patientId: String,
        doctorId: String,
        scheduleId: String,
        queueStatus: String
    ): NetworkResponse<String, ResponseError> =
        apiServices.setSpecialistDoctorQueueCallingStatusUpdate(
            patientId,
            doctorId,
            scheduleId,
            queueStatus
        )

    suspend fun cancelBookedSpecialistDoctor(
        scheduleId: String,
        patientId: String
    ): NetworkResponse<ResponseError, ResponseError> =
        apiServices.cancelBookedSpecialistDoctor(
            scheduleId,
            patientId
        )

    suspend fun getQueueStatusByPatient(
        patientId: String,
        scheduleId: String,
    ): NetworkResponse<ResponseQueueStatus, ResponseError> =
        apiServices.getQueueStatusByPatient(
            patientId,
            scheduleId
        )
}
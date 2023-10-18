package bio.medico.patient.data.repository

import bio.medico.patient.model.apiResponse.RequestDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseDoctorBooking
import bio.medico.patient.model.apiResponse.ResponseError
import bio.medico.patient.model.apiResponse.ResponseQueueStatus
import bio.medico.patient.model.apiResponse.ResponseSpecialized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RepositoryV3(private val apiServiceImp: ApiServiceImp) {


    fun getSpecialistDoctorBookedSchedule(
        userId: String
    ): Flow<NetworkResponse<ResponseSpecialized, ResponseError>> = flow {
        emit(apiServiceImp.getSpecialistDoctorBookedSchedule(userId))
    }.flowOn(Dispatchers.IO)

    fun getSpecializedDoctorList(
        userId: String
    ): Flow<NetworkResponse<ResponseSpecialized, ResponseError>> = flow {
        emit(apiServiceImp.getSpecialistDoctorSchedule(userId))
    }.flowOn(Dispatchers.IO)


    fun setDoctorBooking(
        requestDoctorBooking: RequestDoctorBooking
    ): Flow<NetworkResponse<ResponseDoctorBooking, ResponseError>> = flow {
        emit(apiServiceImp.setDoctorBooking(requestDoctorBooking))
    }.flowOn(Dispatchers.IO)


    fun getSpecialistDoctorQueueList(
        doctorId: String,
        patientId: String,
        scheduleId: String
    ): Flow<NetworkResponse<String, ResponseError>> = flow {
        emit(apiServiceImp.getSpecialistDoctorQueueList(doctorId, patientId, scheduleId))
    }.flowOn(Dispatchers.IO)

    fun setSpecialistDoctorQueueCallingStatusUpdate(
        patientId: String,
        doctorId: String,
        scheduleId: String,
        queueStatus: String
    ): Flow<NetworkResponse<String, ResponseError>> = flow {
        emit(
            apiServiceImp.setSpecialistDoctorQueueCallingStatusUpdate(
                patientId,
                doctorId,
                scheduleId,
                queueStatus
            )
        )
    }.flowOn(Dispatchers.IO)

    fun cancelBookedSpecialistDoctor(
        scheduleId: String,
        patientId: String,
    ): Flow<NetworkResponse<ResponseError, ResponseError>> = flow {
        emit(
            apiServiceImp.cancelBookedSpecialistDoctor(
                scheduleId,
                patientId
            )
        )
    }.flowOn(Dispatchers.IO)

    fun getQueueStatusByPatient(
        patientId: String,
        scheduleId: String,
    ): Flow<NetworkResponse<ResponseQueueStatus, ResponseError>> = flow {
        emit(
            apiServiceImp.getQueueStatusByPatient(
                patientId,
                scheduleId,
                )
        )
    }.flowOn(Dispatchers.IO)

}
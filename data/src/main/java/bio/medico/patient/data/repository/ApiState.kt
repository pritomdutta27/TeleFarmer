package bio.medico.patient.data.repository


/**
Created by Samiran Kumar on 20,September,2022
 **/
sealed class ApiState {
    object Loading : ApiState()
    class Failure(val throwable: Throwable) : ApiState()
    class Success<T>(val data: T) : ApiState()
    object Empty : ApiState()
}



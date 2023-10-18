package bio.medico.patient.data.repository

import java.io.IOException


/**
Created by Samiran Kumar on 25,July,2023
 **/
sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}


//================================
sealed class NetworkResponseModel<out T : Any>{
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponseModel<T>()

    /**
     * Failure response with body
     */
    data class ApiError(val message: String, val code: Int? = -1) : NetworkResponseModel<Nothing>()

}

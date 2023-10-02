package com.farmer.primary.network.utils

/**
 * Created by Pritom Dutta on 13/1/23.
 */

//sealed interface ApiResult<T : Any>
//
//class ApiSuccess<T : Any>(val data: T) : ApiResult<T>
//class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>
//class ApiException<T : Any>(val e: Throwable) : ApiResult<T>

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T>()
    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}


//======================================================================
suspend fun <T : Any> NetworkResult<T>.onSuccess(executable: suspend (T) -> Unit): NetworkResult<T> =
    apply {
        if (this is NetworkResult.Success<T>) {
            executable(data)
        }
    }

suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit): NetworkResult<T> =
    apply {
        if (this is NetworkResult.Error<T>) {
            executable(code, message)
        }
    }

suspend fun <T : Any> NetworkResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception<T>) {
        executable(e)
    }
}
package com.farmer.primary.network.utils

import android.util.Log
import com.farmer.primary.network.model.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response


/**
 * Created by Pritom Dutta on 13/1/23.
 */

fun <T : Any> handleApi(execute: () -> Response<T>): NetworkResult<T> {

    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            val errorResponse = convertErrorBody(response.errorBody())
            val errorMessage: String? = errorResponse?.message
            NetworkResult.Error(
                code = response.code(),
                message = errorMessage ?: response.message()
            )
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}

// If you don't wanna handle api's own
// custom error response then ignore this function
private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
    return try {
        errorBody?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        ErrorResponse(404, exception.message)
    }
}
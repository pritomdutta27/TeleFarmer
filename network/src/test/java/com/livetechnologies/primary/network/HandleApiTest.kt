package com.livetechnologies.primary.network

import com.livetechnologies.primary.network.model.TMDBErrorResponse
import com.livetechnologies.primary.network.model.TmdbApiResponse
import com.livetechnologies.primary.network.utils.*
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by Pritom Dutta on 26/11/22.
 */

class HandleApiTest {

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runTest {
            val data = TmdbApiResponse(12, emptyList(), 0, 0, true)
            val lambdaResult = Response.success(data)
            handleApi { lambdaResult }.onSuccess {
                assertEquals(data, it)
            }
        }
    }

    @Test
    fun `when lambda invalid token then it should emit the result as Error`() {
        val errorBody =
            "{\"status_message\": \"Token Unauthorized\"}".toResponseBody("application/json".toMediaTypeOrNull())

        runTest {
            val error = TMDBErrorResponse(401, "Token Unauthorized")
            val lambdaResult = Response.error<Any>(401, errorBody)
            handleApi { lambdaResult }.onError { code, message ->
                val e = TMDBErrorResponse(code, message.toString())
                assertEquals(e, error)
            }
        }
    }

    @Test
    fun `when lambda throws Throwable then it should emit the result as Exception`() {
        val errorBody = (Throwable().message ?: "")
            .toResponseBody("application/json".toMediaTypeOrNull())
        runTest {
            val lambdaResult = Response.error<Any>(404, errorBody)
            handleApi { lambdaResult }.onException { message ->
                assertEquals(lambdaResult.message(), message)
            }
        }
    }

    @Test
    fun `when lambda httpException then it should emit the result as Exception`() {
        val errorBody = "{\"errors\": [\"Unexpected parameter\"]}".toResponseBody("application/json".toMediaTypeOrNull())

        runTest {
            val errorBody = HttpException(Response.error<Any>(422, errorBody))
//            val lambdaResult = Response.error<Any>(500, errorBody)
            handleApi { Response.success(errorBody.code(),errorBody) }.onError { code, message ->
                assertEquals(errorBody.message, message)
            }
        }
    }
}
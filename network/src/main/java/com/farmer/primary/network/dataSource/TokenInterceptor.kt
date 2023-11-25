package com.farmer.primary.network.dataSource

import bio.medico.patient.model.apiResponse.RequestNewToken
import bio.medico.patient.model.apiResponse.ResponseNewToken
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.UserDevices
import com.farmer.primary.network.utils.AppConstants
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Pritom Dutta on 25/11/23.
 */
class TokenInterceptor(
    private val baseUrl: String,
    private val pref: DataStoreRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        // Check if the response indicates that the access token is expired
        if (response.code == 403) {
            // Call the refresh token API to obtain a new access token
            try {
                runBlocking { callRefreshTokenAPI() }
                // Create a new request with the updated access token
                val requestBuilder = originalRequest.newBuilder()
                    .addHeader("Accept", "application/json")
                val request = requestBuilder.build()
                //response.close()
//            // Retry the request with the new access token
                return chain.proceed(request)
            } catch (e: Exception) {

            }
        }
        return response
    }

//    override fun intercept(chain: Interceptor.Chain): Response {
//
//        var currentRequest = chain!!.request()
//        val currentRequestResponse = chain.proceed(currentRequest)
//
//
//        if (currentRequestResponse.code == 403) {
//            try {
//                //val body = RequestBody.create(contentType, "")
//                val refreshToken = runBlocking { pref.getString(AppConstants.PREF_KEY_REFRESH_TOKEN) ?: "" }
//
//                val authRequest = currentRequest.newBuilder()
//                    .addHeader("Authorization",refreshToken.getBearerToken() )
//                    .url("${baseUrl}surveyer/refreshToken?device_id=${context.getDeviceId()}")
//                    .build()
//
//                val tokenRefreshResponse = chain.proceed(authRequest)
//                if (tokenRefreshResponse.code == 401 || tokenRefreshResponse.code == 403) {
//                    //error in refreshing token
//                    Timber.e("TokenInterceptor ${tokenRefreshResponse.message}")
//                } else if (tokenRefreshResponse.code == 200) {
//                    //save the new token and refresh token in preference and continue with the earlier request
//                    return chain.proceed(
//                        currentRequest.newBuilder()
//                            .build()
//                    )
//                }
//            } catch (e: Exception) {
//
//            }
//        }
//        return currentRequestResponse
//    }

    private suspend fun callRefreshTokenAPI(): ResponseNewToken {

        val apiClient = Retrofit.Builder().baseUrl(baseUrl) // api base url
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)

        val refreshToken = pref.getString(AppConstants.PREF_KEY_REFRESH_TOKEN) ?: ""

        val headerUserInfo: String = UserDevices.getUserDevicesJson("loan")
        val tokenInfo = RequestNewToken(
            refreshToken,
            LocalData.getPhoneNumber(),
            LocalData.getUserUuid(),
            "android"
        )

        val res = apiClient.refreshToken(headerUserInfo, tokenInfo)
        val accessToken = res.accessToken
        accessToken?.let {
            pref.putString(AppConstants.PREF_KEY_ACCESS_TOKEN, accessToken)
        }
        return res

    }


    fun provideOkHttpClient(): OkHttpClient {
        val timeOut = 30
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)

        return httpClient.build()
    }
}
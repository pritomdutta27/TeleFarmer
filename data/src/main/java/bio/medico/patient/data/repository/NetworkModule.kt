package bio.medico.patient.data.repository

import bio.medico.patient.data.AppUrl
import bio.medico.patient.data.BuildConfig
import bio.medico.patient.model.apiResponse.ResponseError
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.Moshi
import com.theroyalsoft.mydoc.apputil.log.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
Created by Samiran Kumar on 25,July,2023
 **/
class NetworkModule {
    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {

            if (retrofit == null) {
                retrofit = createRetrofit(
                    providesMoshi(), getOkHttpClient(true)
                )
            }
            return retrofit!!

        }

        private fun createRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AppUrl.baseurl)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
//                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addConverterFactory(GsonConverterFactory.create(providesGson()))
                .client(client)
                .build()
        }


        private fun getOkHttpClient(isShowLog: Boolean): OkHttpClient {

            return OkHttpClient.Builder().also { client ->

                if (BuildConfig.DEBUG) {
                    if (isShowLog) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }

                    client.addInterceptor(LogUtil.TimberLoggingInterceptor()) // http traffic in Log
                    client.addInterceptor(OkHttpProfilerInterceptor()) // http traffic ok-http profiler stdio plugin
                }

                client.readTimeout(9000, TimeUnit.MILLISECONDS)
                client.connectTimeout(9000, TimeUnit.MILLISECONDS)

            }.build()
        }


        private fun providesMoshi(): Moshi = Moshi.Builder().build()
        private fun providesGson(): Gson = GsonBuilder().setLenient().create()


        fun provideApiServiceBase(): ApiService =
            getRetrofit().create(ApiService::class.java)

        //==============================================================


    }

}

fun <R : Any, T : Any> NetworkResponse<R, T>.getNetworkResponse(): NetworkResponseModel<R> {
    when (this) {
        is NetworkResponse.Success -> {
            return NetworkResponseModel.Success(this.body)
        }

        is NetworkResponse.ApiError -> {
           // Timber.e("ApiError ${this.body.toString()}")
            var message = ""
            try {

                Timber.e("ApiError: ${this.body.toString()}")
                val data = this.body as ResponseError

                message = data?.message ?: ""
            } catch (e: Exception) {
                Timber.e("Error:${e.message}")
            }

            return NetworkResponseModel.ApiError(message, this.code)
        }

        is NetworkResponse.NetworkError -> {
            return NetworkResponseModel.ApiError(this.error.message.toString())
        }

        is NetworkResponse.UnknownError -> {
            Timber.d("UnknownError")
            return NetworkResponseModel.ApiError(this.error?.message.toString())
        }
    }
}

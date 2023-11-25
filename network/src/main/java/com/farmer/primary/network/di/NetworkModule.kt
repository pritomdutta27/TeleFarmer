package com.farmer.primary.network.di

import android.app.Application
import android.content.Context
import com.farmer.primary.network.dataSource.ApiService
import com.farmer.primary.network.dataSource.ApiServiceForImage
import com.farmer.primary.network.dataSource.TokenInterceptor
import com.farmer.primary.network.dataSource.WeatherApi
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.retrofitAdapter.NetworkResultCallAdapterFactory
import com.farmer.primary.network.utils.DataSourceConstants
import com.google.gson.Gson
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.livetechnologies.primary.network.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Pritom Dutta on 13/1/23.
 */


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val REQUEST_TIMEOUT = 30L
    private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10 MB

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        pref: DataStoreRepository
    ): TokenInterceptor {
        return TokenInterceptor(DataSourceConstants.BASE_URL, pref)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @ApplicationContext context: Context,
        mTokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }
        builder
            .addInterceptor(mTokenInterceptor)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getLogInterceptors(BuildConfig.DEBUG))
            .cache(getCache(context))
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("first")
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DataSourceConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("image_upload")
    fun providePictureRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                LocalData.getMetaInfoMetaData()?.imageUploadBaseUrl ?: DataSourceConstants.BASE_URL
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("weather")
    fun provideWeatherRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                DataSourceConstants.WEATHER_URL
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("first") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceForImage(@Named("image_upload") retrofit: Retrofit): ApiServiceForImage {
        return retrofit.create(ApiServiceForImage::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceForWeather(@Named("weather") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }


    @Suppress("SameParameterValue")
    private fun getLogInterceptors(isDebugAble: Boolean = false): Interceptor {

        val builder = LoggingInterceptor.Builder()
            .setLevel(if (isDebugAble) Level.BASIC else Level.NONE)
            .log(Platform.INFO)
            .tag("Test")
            .request("Request")
            .response("Response")
        builder.isDebugAble = isDebugAble
        return builder.build()
    }

    private fun getCache(context: Context) = Cache(context.cacheDir, CACHE_SIZE)
}
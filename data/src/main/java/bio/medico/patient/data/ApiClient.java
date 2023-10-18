package bio.medico.patient.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.theroyalsoft.mydoc.apputil.log.LogUtil;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitTimeOut = null;
    private static ApiInterface apiInterface;


    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppUrl.INSTANCE.getBaseurl())
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient(false))
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getClientTimeOut() {
        if (retrofitTimeOut == null) {
            retrofitTimeOut = new Retrofit.Builder()
                    .baseUrl(AppUrl.INSTANCE.getBaseurl())
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(okHttpClientTime())
                    .build();
        }
        return retrofitTimeOut;
    }

    public static Retrofit getClient(String url) {

        Retrofit customRetrofit = new Retrofit.Builder()
                .baseUrl(url)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient(false))
                .build();

        return customRetrofit;
    }


    //==============================================================================================
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient okHttpClient(boolean isShowLog) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            if (isShowLog) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)); // http traffic in Log
            }
             builder.addInterceptor(new LogUtil.TimberLoggingInterceptor()); // http traffic in Log
            builder.addInterceptor(new OkHttpProfilerInterceptor());   // http traffic ok-http profiler stdio plugin
        }


        builder.readTimeout(9000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(9000, TimeUnit.MILLISECONDS);

        OkHttpClient client = builder.build();

        return client;
    }


    private static OkHttpClient okHttpClientTime() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)); // http traffic in Log
        /*    builder.addInterceptor(new HttpLoggingInterceptor(message -> {

            })); // http traffic in Log*/
            builder.addInterceptor(new OkHttpProfilerInterceptor());   // http traffic ok-http profiler stdio plugin
        }


        builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);

        OkHttpClient client = builder.build();

        return client;
    }

    //===================================================================================
    public static <S> S getApiInterface(Class<S> serviceClass, String baseUrl) {

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient(false))
                .build();

        return builder.create(serviceClass);
    }


    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }
        return apiInterface;
    }


    public static ApiInterface getApiInterfaceTimeout() {
        return ApiClient.getClientTimeOut().create(ApiInterface.class);
    }


    public static ApiInterface getApiInterface(String baseUrl) {
        return ApiClient.getClient(baseUrl).create(ApiInterface.class);
    }

}

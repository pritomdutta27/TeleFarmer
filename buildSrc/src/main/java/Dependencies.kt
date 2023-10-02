import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//run this to get updated dependencies ./gradlew dependencyUpdates -DoutputDir=VersionReport
object Versions {
    const val gradlePlugin = "4.2.2"

    const val kotlin = "1.8.21"
    const val coreKtx = "1.10.0"

    const val appCompat = "1.3.1"
    const val constraintLayout = "2.1.0"
    const val fragmentKtx = "2.5.3"
    const val fragment = "1.5.7"
    const val navigation = "2.4.2"
    const val lifecycleRuntimeKTX = "2.3.1"

    const val lifecycleVersion = "2.5.1"

    const val material = "1.8.0"

    const val rxAndroid = "3.0.0"
    const val rxJava = "3.1.0"

    const val retrofit = "2.9.0"
    const val gson = "2.8.9"
    const val loggingInterceptor = "3.1.0"
    const val moshi = "1.12.0"
    const val stetho = "1.5.1"

    const val sdpssp = "1.0.6"

    const val glide = "4.12.0"

    const val jUnit = "4.13.2"
    const val extJunit = "1.1.3"
    const val espressoCore = "3.2.0"
    const val mockWebServer = "4.9.1"
    const val mockito = "3.8.0"
    const val assertj = "3.20.2"
    const val truth = "1.0.1"
    const val androidxTestRunner = "1.4.0"
    const val allOpen = "1.5.21"
    const val androidArchCore = "2.1.0"
    const val androidXTestRule = "1.4.0"
    const val navigationTesting = "2.3.0-alpha01"
    const val espressoCContribAndIntent = "3.2.0"
    const val espressoIdlingAndConcurrent = "3.2.0"

    const val paging_version = "3.1.0"

    const val indication_pageview = "1.2.1"
    const val otp_version = "v1.1.2-ktx"

    const val firebase_bom = "31.0.2"
    const val firebase_messaging_ktx_version = "23.0.6"
    const val firebase_messaging_version = "23.0.6"

    const val lottieVersion = "5.2.0"
    const val dataStoreVersion = "1.0.0"

    const val circleimageview = "3.1.0"

    const val timberVersion = "5.0.1"

    const val hilt_version = "2.44"
    const val hiltNavigation = "1.0.0"

    const val coil_version = "2.3.0"

    const val coroutines_version = "1.6.4"
}


/**
 * To define plugins
 */
object BuildPlugins {
    val androidBuildTools = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    val kotlinPlugins = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
    val navigationSafeArg =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    val allOpenPlugin = "org.jetbrains.kotlin:kotlin-allopen:${Versions.allOpen}"
}

/**
 * To define dependencies
 */
object KotlinDependencies {
    val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val fragmentNavigationKTX = "androidx.navigation:navigation-fragment-ktx:${Versions.fragmentKtx}"
    val navigationUIKTX = "androidx.navigation:navigation-ui-ktx:${Versions.fragmentKtx}"
    val fragmentKTX = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

object AndroidXSupportDependencies {
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val lifecycleRuntimeKTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKTX}"
}

object MaterialDesignDependencies {
    val materialDesign = "com.google.android.material:material:${Versions.material}"
}

object TestingDependencies {
    val junit = "junit:junit:${Versions.jUnit}"
    val androidExtJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    val androidTestRunner = "androidx.test:runner:${Versions.androidxTestRunner}"
    val androidTestRule = "androidx.test:rules:${Versions.androidXTestRule}"
    val androidEspressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    val truth = "com.google.truth:truth:${Versions.truth}"
    val androidArchCoreTesting = "androidx.arch.core:core-testing:${Versions.androidArchCore}"
    val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentKtx}"
    val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navigationTesting}"
    val espressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espressoCContribAndIntent}"
    val espressoIntent =
        "androidx.test.espresso:espresso-intents:${Versions.espressoCContribAndIntent}"
    val espressoConcurrent =
        "androidx.test.espresso.idling:idling-concurrent:${Versions.espressoIdlingAndConcurrent}"
    val espressoIdling =
        "androidx.test.espresso:espresso-idling-resource:${Versions.espressoIdlingAndConcurrent}"


}

object Libraries {
    val hiltNavigation = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigation}"

    val coroutines_lib =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    val coroutines_android_lib =  "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"

    //For instrumentation tests
    val hiltInstrumentation = "com.google.dagger:hilt-android-testing:${Versions.hilt_version}"
    val hiltInsAnnotationProcessor = "com.google.dagger:hilt-android-testing:${Versions.hilt_version}"

    //For local unit tests
    val hiltUnitTest = "com.google.dagger:hilt-android-testing:${Versions.hilt_version}"
    val hiltUnitTestAnnotationProcessor = "com.google.dagger:hilt-compiler:${Versions.hilt_version}"

    //rxjava and rxAndroid
    val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"

    //logging interceptor
   // val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    //network interceptor
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    val stethoJSRhino = "com.facebook.stetho:stetho-js-rhino:${Versions.stetho}"

    //moshi
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlinCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    val moshiKotlin = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    //retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"

    val circleimageview = "de.hdodenhof:circleimageview:${Versions.circleimageview}"

    //Timber
    val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    //DP Lib
    val sdp = "com.intuit.sdp:sdp-android:${Versions.sdpssp}"
    val ssp = "com.intuit.ssp:ssp-android:${Versions.sdpssp}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"

    val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging_version}"
    val rxpaging = "androidx.paging:paging-rxjava3:${Versions.paging_version}"

    //Indicator
    val indicator_lib = "com.github.zhpanvip:viewpagerindicator:${Versions.indication_pageview}"
    val pinview = "com.github.aabhasr1:OtpView:${Versions.otp_version}"

    //Firebase
    val firebase_bom_lib = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    val firebase_analytics = "com.google.firebase:firebase-analytics"
    val firebase_analytics_ktx = "com.google.firebase:firebase-analytics-ktx"
    val firebase_crashlytics = "com.google.firebase:firebase-crashlytics"
    val firebase_messaging_ktx_lib =
        "com.google.firebase:firebase-messaging-ktx:${Versions.firebase_messaging_ktx_version}"
    val firebase_messaging_lib =
        "com.google.firebase:firebase-messaging:${Versions.firebase_messaging_version}"

    //Lottie
    val lottie_lib = "com.airbnb.android:lottie:${Versions.lottieVersion}"

    val datastore_lib =  "androidx.datastore:datastore-preferences:${Versions.dataStoreVersion}"

    val hilt_android_lib =  "com.google.dagger:hilt-android:${Versions.hilt_version}"
    val hilt_android_compiler_lib =  "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
    val hiltAnnotationProcessor = "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"

    val coil = "io.coil-kt:coil:${Versions.coil_version}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleVersion}"
    const val viewModelSaveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions::${Versions.lifecycleVersion}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Versions.lifecycleVersion}"

    //logging interceptor
    val loggingInterceptor = "com.github.ihsanbal:LoggingInterceptor:${Versions.loggingInterceptor}"
}

fun getDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")
    val formatted = current.format(formatter)
    return formatted
}
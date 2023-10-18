plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.livetechnologies.primary.network"
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion

        testInstrumentationRunner = BuildConfig.testRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation (KotlinDependencies.coreKtx)
    implementation (AndroidXSupportDependencies.appCompat)

    //Hilt
    implementation(Libraries.hilt_android_lib)
    kapt(Libraries.hilt_android_compiler_lib)

    implementation(Libraries.paging)
    implementation(Libraries.rxpaging)

    //Coroutines
    implementation(Libraries.coroutines_lib)
    implementation(Libraries.coroutines_android_lib)

    implementation(Libraries.datastore_lib)

    implementation(Libraries.gson)
    implementation(Libraries.gsonConverter)

    //Retrofit
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshiConverter)
    implementation(Libraries.retrofitRxAdapter)

    implementation(Libraries.loggingInterceptor) {
        exclude(group = "org.json", module = "json")
    }

    implementation("com.github.SamiranKumar:common-util-android:1.13")

    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation(project(":model"))

    //Test Library
    testImplementation (TestingDependencies.junit)
//    androidTestImplementation (TestingDependencies.ext_jUnit_lib)
//    testImplementation(TestingDependencies.truth_test_lib)
//    testImplementation(TestingDependencies.coroutines_test)
}
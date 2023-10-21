plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "bio.medico.patient.data"
    compileSdk = BuildConfig.compileSdkVersion


    defaultConfig {
        minSdk = BuildConfig.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    implementation(project(":appUtil"))
    implementation(project(":common"))
    implementation(project(":model"))
    implementation(project(":network"))

    implementation(KotlinDependencies.kotlinStd)
    implementation(KotlinDependencies.coreKtx)
    implementation(AndroidXSupportDependencies.appCompat)
    implementation(MaterialDesignDependencies.materialDesign)

    implementation(Libraries.timber)
    implementation(Libraries.commonUtil)


    //retrofit
    implementation(Libraries.retrofit)
    implementation(Libraries.gsonConverter)
    implementation(Libraries.retrofitMoshiConverter)
//    implementation(Libraries.retrofit_converter_scalars)
    implementation(Libraries.moshi)
//    kapt(Libraries.moshi_kotlin_codegen)

    implementation(Libraries.okhttp)
    implementation(Libraries.okhttp_logging_interceptor)
    implementation(Libraries.okhttpprofilerLog)
    implementation(Libraries.retrofit_converter_scalars)

    implementation(Libraries.coroutines_lib)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
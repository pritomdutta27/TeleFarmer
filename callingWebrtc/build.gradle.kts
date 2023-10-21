plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "bio.medico.patient.callingWebrtc"
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion

        testInstrumentationRunner = BuildConfig.testRunner
        //consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":assets"))
    implementation(project(":appUtil"))
    implementation(project(":common"))
    implementation(project(":model"))
//    implementation(project(":data"))
    implementation(project(":socketUtils"))
    implementation(project(":network"))
//    implementation(project(":firebaseManager"))

    implementation(KotlinDependencies.kotlinStd)
    implementation(KotlinDependencies.coreKtx)
    implementation(AndroidXSupportDependencies.appCompat)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(KotlinDependencies.fragmentNavigationKTX)
    implementation(KotlinDependencies.navigationUIKTX)

    implementation(Libraries.timber)
    implementation(Libraries.commonUtil)
    implementation(Libraries.sdp)

    //Hilt
    implementation(Libraries.hilt_android_lib)
    kapt(Libraries.hilt_android_compiler_lib)
    kapt(Libraries.hiltAnnotationProcessor)

//    implementation(Libraries.lifecycle_extensions)
    implementation(Libraries.lifecycle_viewmodel)
    implementation(Libraries.viewModel)

//lottie
    implementation("com.airbnb.android:lottie:6.1.0")

    implementation("org.webrtc:google-webrtc:1.0.32006")

    implementation(Libraries.coroutines_lib)

//    implementation(Deps.easypermissions)
    implementation(Libraries.circleimageview)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
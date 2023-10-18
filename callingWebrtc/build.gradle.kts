plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "bio.medico.patient.callingWebrtc"
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
    configurations.all {
        resolutionStrategy {
            force(AndroidXSupportDependencies.appCompat)
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
    implementation(project(":data"))
    implementation(project(":socketUtils"))
//    implementation(project(":firebaseManager"))

    implementation(KotlinDependencies.kotlinStd)
    implementation(KotlinDependencies.coreKtx)
    implementation(AndroidXSupportDependencies.appCompat)
    implementation(MaterialDesignDependencies.materialDesign)

    implementation(Libraries.timber)
    implementation(Libraries.commonUtil)
    implementation(Libraries.sdp)


    implementation("org.webrtc:google-webrtc:1.0.32006")

    implementation(Libraries.coroutines_lib)

//    implementation(Deps.easypermissions)
    implementation(Libraries.circleimageview)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
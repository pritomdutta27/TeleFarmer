plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "bio.medico.patient.assets"
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

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(KotlinDependencies.kotlinStd)
    implementation(KotlinDependencies.coreKtx)
    implementation(AndroidXSupportDependencies.appCompat)
    implementation(MaterialDesignDependencies.materialDesign)

    implementation(Libraries.sdp)
    implementation(Libraries.ssp)
}
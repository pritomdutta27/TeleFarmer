plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    signingConfigs {
        create("release") {
            storeFile =
                file("/Users/pritomdutta/Desktop/My Learning/TeleFarmer/tele_farmer")
            storePassword = "!@#$%^&*"
            keyAlias = "tele"
            keyPassword = "!@#$%^&*"
        }
    }

    compileSdk = BuildConfig.compileSdkVersion
    buildToolsVersion = BuildConfig.buildToolsVersion
    namespace = BuildConfig.applicationID

    defaultConfig {
        applicationId = BuildConfig.applicationID
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = BuildConfig.testRunner

        setProperty("archivesBaseName", "Tele Farmer" + "-v" + versionCode + "_" + getDate())
        signingConfig = signingConfigs.getByName("release")
        multiDexEnabled = true
        testFunctionalTest = false
        testHandleProfiling = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
        targetSdk = 32
        minSdk = 27

        renderscriptTargetApi = 21
        renderscriptSupportModeEnabled = true
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            isPseudoLocalesEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            isPseudoLocalesEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    implementation(project(":assets"))
    implementation(project(":common"))
    implementation(project(":model"))
//    implementation(project(":data"))
    implementation(project(":callingWebrtc"))
    implementation(project(":socketUtils"))

    implementation(KotlinDependencies.coreKtx)
    implementation(AndroidXSupportDependencies.appCompat)
    implementation(AndroidXSupportDependencies.constraintLayout)
    implementation(MaterialDesignDependencies.materialDesign)
    implementation(KotlinDependencies.fragmentNavigationKTX)
    implementation(KotlinDependencies.navigationUIKTX)

    //image round
    implementation(Libraries.circleimageview)

    implementation(Libraries.retrofitMoshiConverter)

    implementation(Libraries.sdp)
    implementation(Libraries.ssp)

    implementation(Libraries.pinview)

    implementation(project(":appUtil"))
    implementation(project(":network"))

    //NumberProgressBar
    implementation("com.daimajia.numberprogressbar:library:1.4@aar")

    //lottie
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation(Libraries.indicator_lib)

    implementation ("com.jakewharton.timber:timber:5.0.1") //timber

    implementation("org.webrtc:google-webrtc:1.0.32006")

    implementation("com.wx.wheelview:wheelview:1.3.3")
    implementation("cn.aigestudio.wheelpicker:WheelPicker:1.1.3")

    implementation("com.guolindev.permissionx:permissionx:1.6.1")

    implementation("io.socket:socket.io-client:2.0.1") {
        exclude(group = "org.json", module = "json")
    }

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.vanniktech:android-image-cropper:4.5.0")

    implementation(Libraries.paging)
    implementation(Libraries.rxpaging)

    //Hilt
    implementation(Libraries.hilt_android_lib)
    implementation("androidx.browser:browser:1.7.0")
    kapt(Libraries.hilt_android_compiler_lib)
    kapt(Libraries.hiltAnnotationProcessor)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation("pub.devrel:easypermissions:3.0.0")
    implementation("com.github.SamiranKumar:common-util-android:1.13")
//    implementation ("id.zelory:compressor:3.0.0")

    //retrofit
    val retrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")   // gson
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    //chatting
    implementation("com.vanniktech:emoji-google:0.5.1")
    implementation("com.github.captain-miao:optroundcardview:1.0.0")
    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("id.zelory:compressor:2.1.1")

    implementation("com.github.piasy:BigImageViewer:1.8.1")
    implementation("com.github.piasy:FrescoImageLoader:1.8.1")
    implementation("com.github.piasy:GlideImageLoader:1.8.1")
    implementation("com.github.piasy:ProgressPieIndicator:1.8.1")
    implementation("com.github.piasy:FrescoImageViewFactory:1.8.1")
    implementation ("com.github.piasy:GlideImageViewFactory:1.8.1")


    //testing
    testImplementation(TestingDependencies.junit)
    testImplementation(TestingDependencies.assertj)
    testImplementation(TestingDependencies.truth)
    testImplementation(TestingDependencies.mockito)
}
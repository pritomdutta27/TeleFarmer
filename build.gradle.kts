// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.4.2" apply false
    id ("com.android.library") version "7.4.2" apply false
    id ("org.jetbrains.kotlin.android") version Versions.kotlin apply false
    id ("com.google.dagger.hilt.android") version Versions.hilt_version apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    kotlin("plugin.serialization") version "1.8.21"
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
    }
}
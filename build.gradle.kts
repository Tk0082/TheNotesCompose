// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin}")
        classpath ("com.google.devtools.ksp:symbol-processing-gradle-plugin:${libs.versions.kspVersion}")
        classpath ("com.android.tools.build:gradle:8.0.0")

    }
}
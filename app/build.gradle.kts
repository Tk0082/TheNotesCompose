plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.betrend.cp.thenotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.betrend.cp.thenotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        //kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
//        @Suppress("DEPRECATION")
//        kotlinCompilerVersion  = "1.8.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    //buildToolsVersion = "35.0.0"
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("ksp.incremental", "false")
}

dependencies {

    implementation(libs.kotlinLib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // RoomDB
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    implementation("androidx.compose.compiler:compiler:1.5.15")

    // Animated BottomNavigationBar
    implementation(libs.animated.navigation.bar)

    // Material3
    implementation ("androidx.compose.material3:material3:1.3.1")
    implementation ("androidx.compose.material:material:1.7.8")

    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.datastore.preferences)
    implementation(libs.androidx.navigation.compose)
}
//Permitir referências ao código gerado
kapt {
    correctErrorTypes = true
    useBuildCache = true
}
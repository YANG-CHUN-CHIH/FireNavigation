plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.firenavigation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firenavigation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // CameraX Core Library
    implementation("androidx.camera:camera-core:1.4.0")

    // CameraX Lifecycle Integration
    implementation("androidx.camera:camera-lifecycle:1.4.0")

    // CameraX Camera2 Support (if needed)
    implementation("androidx.camera:camera-camera2:1.4.0")

    // CameraX Preview View (UI component to display the camera preview)
    implementation("androidx.camera:camera-view:1.4.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
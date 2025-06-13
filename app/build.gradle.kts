plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.theweather"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.theweather"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // UI Bundle
    implementation(libs.bundles.compose.ui)
    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.bundles.compose.ui.debug)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":feature-weather-api"))
    implementation(project(":feature-weather-impl"))
    implementation(project(":feature-search-city"))

    // Testing
    testImplementation(libs.bundles.unit.testing)
}
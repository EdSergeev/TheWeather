plugins {
    id("theweather.lib")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.feature_weather_impl"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose.ui)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.google.play.services.location)


    implementation(project(":core-data"))
    implementation(project(":feature-weather-api"))
}
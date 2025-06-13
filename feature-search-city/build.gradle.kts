plugins {
    id("theweather.lib")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.feature_search_city"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose.ui)
    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.bundles.compose.ui.debug)

    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":feature-weather-api"))
}
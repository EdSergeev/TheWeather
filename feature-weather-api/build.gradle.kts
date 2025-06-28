plugins {
    id("theweather.lib")
}

android {
    namespace = "com.example.feature_weather_api"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(project(":core-data"))
}
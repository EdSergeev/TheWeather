plugins {
    id("theweather.lib")
}

android {
    namespace = "com.example.feature_weather_api"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(project(":core-data"))
}
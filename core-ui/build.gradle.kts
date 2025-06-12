plugins {
    id("theweather.lib")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.core_ui"
}

dependencies {
    implementation(libs.bundles.compose.ui)
    implementation(platform(libs.androidx.compose.bom))
}
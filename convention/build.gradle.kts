plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

group = "com.example.theweather"

dependencies {
//    compileOnly(libs.android.gradlePlugin)
//    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly("com.android.tools.build:gradle:8.1.0") // Use your AGP version
    // Kotlin Gradle Plugin
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "theweather.lib"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
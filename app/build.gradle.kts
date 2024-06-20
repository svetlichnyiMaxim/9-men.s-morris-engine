plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

tasks.withType<Test>() {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

android {
    namespace = "com.kroune.mensMorris"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kroune.mensMorris"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
    compileSdk = 34
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.0-beta02")
    implementation("androidx.compose.material3:material3:1.3.0-beta02")
    implementation("androidx.navigation:navigation-compose:2.8.0-beta02")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.0-beta02")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

    // ktor
    implementation("io.ktor:ktor-client-core:3.0.0-beta-1")
    implementation("io.ktor:ktor-client-okhttp:3.0.0-beta-1")
    implementation("io.ktor:ktor-client-auth:3.0.0-beta-1")

    // test dependencies
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    testImplementation(platform("org.junit:junit-bom:5.11.0-M2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0-M2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")

    // my own dependencies
    implementation("com.github.kroune:9-men-s-morris-lib:177de1d29f")
    implementation("com.github.kroune:9-men-s-morris-shared:6d370a2760")

    // di
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
}

kapt {
    correctErrorTypes = true
}

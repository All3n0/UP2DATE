plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "io.eldohub.feature.articles"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":domain"))
    implementation("androidx.compose.ui:ui:1.6.0")
// or newer
    implementation("androidx.compose.material3:material3:1.2.0")
// or newer
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.2")

    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.accompanist)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.indicator)

    implementation(libs.coil.compose)

    implementation(libs.ktor.android)

    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)

    implementation(libs.koin.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.test)
    implementation(libs.koin.junit)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
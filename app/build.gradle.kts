plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.yummyplanner"
    compileSdk {
        version = release(36)
    }

    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.yummyplanner"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.viewpager2)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.material:material:1.11.0")

    // Navigation component (optional, for advanced nav)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Lottie animation
    implementation(libs.lottie)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.navigation.safeargs)
}

// Automatically delete duplicate ic_launcher.png files that conflict with .webp versions
project.fileTree("src/main/res") {
    include("**/ic_launcher.png")
}.forEach { it.delete() }

android {
    namespace = "com.example.yummyplanner"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.yummyplanner"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.circleimageview)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    // Firestore
    implementation("com.google.firebase:firebase-firestore")
    // youtube video
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:13.0.0")

    // Navigation component
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


    //RXJava
    implementation("io.reactivex.rxjava3:rxjava:3.1.10")
    //RXJava with android to be lifecycle aware
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

    //retrofit with RXJava
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    //room with RXJava
    implementation("androidx.room:room-rxjava3:2.6.1")
}

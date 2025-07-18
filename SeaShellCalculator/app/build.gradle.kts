plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.proyectoPdm.seashellinc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.proyectoPdm.seashellinc"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation(libs.play.services.ads)
    implementation(libs.androidx.foundation)
    ksp(libs.androidx.room.compiler)
//    annotationProcessor ("androidx.room:room-compiler:2.7.2")
    implementation ("androidx.room:room-ktx:2.6.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    //navegación
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.json)

    //billing
    implementation("com.android.billingclient:billing:6.0.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //---------------------------------------------------

//    //Room
//    implementation ("androidx.room:room-runtime:2.6.1")
//    ksp(libs.androidx.room.compiler)
////    annotationProcessor ("androidx.room:room-compiler:2.7.2")
//    implementation ("androidx.room:room-ktx:2.6.1")
//
//    //Retrofit
//    implementation(libs.retrofit)
//    implementation("androidx.fragment:fragment-ktx:1.6.1")
//    implementation("androidx.activity:activity-ktx:1.7.2")
//    implementation(libs.converter.gson)
//
//    //Hilt
//    implementation("com.google.dagger:hilt-android:2.56.2")
//    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
//
//    //navegación
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.coil.compose)
//    implementation(libs.kotlinx.serialization.json)
//
//    // Room components
//    implementation("androidx.room:room-runtime:2.6.1")
//    implementation("androidx.room:room-compiler:2.6.1")
//
//// Kotlin extensions and coroutines support for Room
//    implementation("androidx.room:room-ktx:2.6.1")
//
//// Retrofit for API communication
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//// Gson
//    implementation("com.google.code.gson:gson:2.10.1")
//
//// Coroutine support
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material.icons.core)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
}
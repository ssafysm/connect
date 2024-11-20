plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.gms.google-services") // 구글 서비스
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "1.5.0"
}


android {
    namespace = "com.ssafy.smartstore_jetpack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.smartstore_jetpack"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.androidx.datastore.preferences.core.jvm)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    // indicator
    implementation ("me.relex:circleindicator:2.1.6")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // FCM 사용 위한 plugins
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation ("com.google.firebase:firebase-messaging-ktx")

    // Beacon 사용위한 Dependency 추가
    // Android beacon Library. https://github.com/AltBeacon/android-beacon-library
    implementation ("org.altbeacon:android-beacon-library:2.19")

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // OkHttp3
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Coil
    implementation("io.coil-kt:coil:2.0.0-rc03")

    // Google map API
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.0-rc01")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // firebase 사용에 필요한 의존성 추가 firebase + database
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-database-ktx")

    // firebase auth 에서 필요한 의존성 추가
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

}

kapt {
    correctErrorTypes = true
}
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.ksp)
    id("kotlin-parcelize")
}

android {

    namespace = "com.demo.one"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.demo.one"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    viewBinding.isEnabled = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions{
        jvmTarget = "17"
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.android.material)
    //navigation
    implementation(libs.androidx.navigation.fragment.base)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.base)
    implementation(libs.androidx.navigation.ui.ktx)
    //splashscreen
    implementation(libs.androidx.splashscreen)
    //paging
    implementation(libs.androidx.paging)
    //room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //lottie
    implementation(libs.lottie)
    //fresco
    implementation(libs.fresco.base)
    implementation(libs.fresco.animated.base)
    implementation(libs.fresco.animated.gif)
    implementation(libs.fresco.animated.webp)
    //microscope
    implementation(libs.microscope.lifecycle)
    implementation(libs.microscope.fresco)
}

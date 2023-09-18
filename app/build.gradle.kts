@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    id("com.bytedance.rhea-trace")
}

android {

    namespace = "com.demo.one"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.one.myapplication"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        ndk {
//            abiFilters("armeabi-v7a", "x86", "x86_64")
//        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    rheaTrace {
        compilation {
            traceFilterFilePath = "${project.rootDir}/trace-filter/traceFilter.txt"
            needPackageWithMethodMap = true
            applyMethodMappingFilePath = ""
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":base"))

    implementation(libs.kotlin.stdlib.jdk7)
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
    //lottie
    implementation(libs.lottie)
    //btrace
    implementation(libs.btrace.core)
}

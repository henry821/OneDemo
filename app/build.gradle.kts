@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    id("one-demo")
    id("com.bytedance.rhea-trace")
}

android {

    namespace = "com.demo.one"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.demo.one"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("armeabi-v7a","arm64-v8a", "x86", "x86_64")
        }
    }

    viewBinding.isEnabled = true

    rheaTrace {
        compilation {
            traceFilterFilePath = "${project.rootDir}/trace-filter/traceFilter.txt"
            needPackageWithMethodMap = true
            applyMethodMappingFilePath = ""
        }
    }

    methodTime {
        includePackages = arrayOf("com.demo.one")
        logTag = "OneDemoPlugin"
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":base"))

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
    //lottie
    implementation(libs.lottie)
    //btrace
    implementation(libs.btrace.core)
}

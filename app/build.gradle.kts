plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(ProjectConfig.compileSdkVersion)
    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdkVersion(ProjectConfig.minSdkVersion)
        targetSdkVersion(ProjectConfig.targetSdkVersion)
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", Version.kotlin))
    implementation(Dependency.appcompat)
    implementation(Dependency.constraintlayout)
    implementation(Dependency.recyclerview)
    implementation(Dependency.core_ktx)
    //lottie
    implementation(Dependency.lottie)

}
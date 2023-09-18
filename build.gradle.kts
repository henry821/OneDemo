// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.bytedance.btrace", "rhea-gradle-plugin", libs.versions.rhea.trace.get())
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin) apply false
}
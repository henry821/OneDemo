@file:Suppress("DSL_SCOPE_VIOLATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "\\localmaven")
    }
    dependencies {
        classpath(libs.btrace.gradle.plugin)
        classpath(libs.one.demo.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin) apply false
}
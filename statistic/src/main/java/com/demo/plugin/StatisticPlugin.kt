package com.demo.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StatisticPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("--加载插件--")
    }
}
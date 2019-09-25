package com.demo.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StatisticsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "自定义插件"
    }
}
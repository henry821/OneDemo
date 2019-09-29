package com.demo.plugin

import com.android.build.gradle.BaseExtension
import com.demo.transform.StatisticsTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class StatisticsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "自定义插件"
        def android = project.extensions.getByType(BaseExtension)
        android.registerTransform(new StatisticsTransform())
    }
}
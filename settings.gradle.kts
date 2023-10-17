pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "OneDemo"
include(":app")
include(":base")
include(":plugin")

include(":designpattern")
include(":leetcode")
include(":concurrency")

// google相机示例
//include ':samples:camera-samples:Camera2Basic:app'
//include ':samples:camera-samples:Camera2Basic:utils'
//
//include ':samples:camera-samples:Camera2Extensions:app'
//
//include ':samples:camera-samples:Camera2SlowMotion:app'
//include ':samples:camera-samples:Camera2SlowMotion:utils'
//
//include ':samples:camera-samples:Camera2Video:app'
//include ':samples:camera-samples:Camera2Video:utils'
 
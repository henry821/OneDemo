package com.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.demo.utils.*

/**
 * Description 主Application
 * Author henry
 * Date   2022/12/20
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                activity.lifecycleLogV("onPreCreate")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.lifecycleLogCreate()
            }

            override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPostCreated(activity, savedInstanceState)
                activity.lifecycleLogV("onPostCreate")
            }

            override fun onActivityPreStarted(activity: Activity) {
                super.onActivityPreStarted(activity)
                activity.lifecycleLogV("onPreStart")
            }

            override fun onActivityStarted(activity: Activity) {
                activity.lifecycleLogStart()
            }

            override fun onActivityPostStarted(activity: Activity) {
                super.onActivityPostStarted(activity)
                activity.lifecycleLogV("onPostStart")
            }

            override fun onActivityPreResumed(activity: Activity) {
                super.onActivityPreResumed(activity)
                activity.lifecycleLogV("onPreResume")
            }

            override fun onActivityResumed(activity: Activity) {
                activity.lifecycleLogResume()
            }

            override fun onActivityPostResumed(activity: Activity) {
                super.onActivityPostResumed(activity)
                activity.lifecycleLogV("onPostResume")
            }

            override fun onActivityPrePaused(activity: Activity) {
                super.onActivityPrePaused(activity)
                activity.lifecycleLogV("onPrePause")
            }

            override fun onActivityPaused(activity: Activity) {
                activity.lifecycleLogPause()
            }

            override fun onActivityPostPaused(activity: Activity) {
                super.onActivityPostPaused(activity)
                activity.lifecycleLogV("onPostPause")
            }

            override fun onActivityPreStopped(activity: Activity) {
                super.onActivityPreStopped(activity)
                activity.lifecycleLogV("onPreStop")
            }

            override fun onActivityStopped(activity: Activity) {
                activity.lifecycleLogStop()
            }

            override fun onActivityPostStopped(activity: Activity) {
                super.onActivityPostStopped(activity)
                activity.lifecycleLogV("onPostStop")
            }

            override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
                super.onActivityPreSaveInstanceState(activity, outState)
                activity.lifecycleLogV("onPreSaveInstanceState")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                activity.lifecycleLogSaveInstanceState()
            }

            override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
                super.onActivityPostSaveInstanceState(activity, outState)
                activity.lifecycleLogV("onPostSaveInstanceState")
            }

            override fun onActivityPreDestroyed(activity: Activity) {
                super.onActivityPreDestroyed(activity)
                activity.lifecycleLogV("onPreDestroy")
            }

            override fun onActivityDestroyed(activity: Activity) {
                activity.lifecycleLogDestroy()
            }

            override fun onActivityPostDestroyed(activity: Activity) {
                super.onActivityPostDestroyed(activity)
                activity.lifecycleLogV("onPostDestroy")
            }

        })
    }

}
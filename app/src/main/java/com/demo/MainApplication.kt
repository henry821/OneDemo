package com.demo

import android.app.Application
import com.demo.modules.monitor.FpsMonitor

/**
 * Description 主Application
 * Author henry
 * Date   2022/12/20
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FpsMonitor().start()
    }
}
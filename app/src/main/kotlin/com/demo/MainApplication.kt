package com.demo

import android.app.Application
import com.facebook.drawee.backends.pipeline.DraweeConfig
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.microscope.lifecycle.LifecycleMonitor

/**
 * Description 主Application
 * Author henry
 * Date   2022/12/20
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        LifecycleMonitor.start(this)

        val config = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build()
        val draweeConfig = DraweeConfig.newBuilder().setDrawDebugOverlay(true).build()
        Fresco.initialize(this, config, draweeConfig)

//        FrescoMonitor.start()
    }

}
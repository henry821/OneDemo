package com.demo

import android.app.Application
import com.demo.modules.fresco.ClosableReferenceLeakTrackerImpl
import com.demo.modules.fresco.ImageCacheStatsTrackerImpl
import com.demo.modules.fresco.ObservableBitmapMemoryCacheFactory
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

        val config = ImagePipelineConfig.newBuilder(this)
            .setImageCacheStatsTracker(ImageCacheStatsTrackerImpl())
            .setCloseableReferenceLeakTracker(ClosableReferenceLeakTrackerImpl())
            .setBitmapMemoryCacheFactory(ObservableBitmapMemoryCacheFactory())
            .build()
        Fresco.initialize(this, config)
    }

}
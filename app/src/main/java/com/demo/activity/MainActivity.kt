package com.demo.activity

import com.demo.google.GoogleSampleEntranceActivity
import com.demo.one.R
import com.demo.widgets.OverviewFloatView

class MainActivity : EntranceActivity() {

    private lateinit var overviewFloatView: OverviewFloatView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        overviewFloatView = OverviewFloatView(this)
        overviewFloatView.show()
    }

    override fun initData(): List<MainData> {
        val list = ArrayList<MainData>()
        list.add(MainData(getString(R.string.lottie_activity), LottieActivity::class.java))
        list.add(MainData(getString(R.string.capture_activity), CaptureActivity::class.java))
        list.add(MainData(getString(R.string.executor_service_activity), ThreadPoolExecutorActivity::class.java))
        list.add(MainData(getString(R.string.image_view_scale_type_activity), ImageViewScaleTypeActivity::class.java))
        list.add(MainData(getString(R.string.animator_app_name), AnimatorActivity::class.java))
        list.add(MainData(getString(R.string.google_sample_activity), GoogleSampleEntranceActivity::class.java))
        return list
    }

}
package com.demo.google

import com.demo.activity.EntranceActivity
import com.demo.google.storage.StorageEntranceActivity
import com.demo.one.R

/**
 * 谷歌示例入口
 */
class GoogleSampleEntranceActivity : EntranceActivity() {

    override fun initData(): List<MainData> {
        val list = ArrayList<MainData>()
        list.add(MainData(getString(R.string.google_sample_storage_entrance), StorageEntranceActivity::class.java))
        return list
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

}
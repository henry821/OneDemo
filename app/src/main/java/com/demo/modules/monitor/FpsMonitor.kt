package com.demo.modules.monitor

import android.util.Log
import android.view.Choreographer

/**
 * Description 帧率检测
 * Author henry
 * Date   2022/12/20
 */
class FpsMonitor : Choreographer.FrameCallback {
    companion object {
        private const val TAG = "OdFrameCallback"
        private const val MONITOR_INTERVAL = 160L
    }

    private var mStartFrameTime: Long = 0L
    private var mFrameCount = 0

    fun start() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    override fun doFrame(frameTimeNanos: Long) {
        if (mStartFrameTime == 0L) {
            mStartFrameTime = frameTimeNanos
        }
        val intervalMs = (frameTimeNanos - mStartFrameTime) / 1_000_000 //纳秒-->毫秒
        //统计周期是160ms
        //1.如果统计间隔小于一个周期（160ms），则累加帧数
        //2.如果统计间隔大于一个周期（160ms），则计算出帧率(帧/秒)
        if (intervalMs > MONITOR_INTERVAL) {
            val fps = mFrameCount / intervalMs.toDouble() * 1000 // 帧/毫秒*1000 --> 帧/秒
            Log.e(TAG, "fps:${fps.toInt()}")
            mFrameCount = 0
            mStartFrameTime = 0
        } else {
            ++mFrameCount
        }
        Choreographer.getInstance().postFrameCallback(this)
    }

}
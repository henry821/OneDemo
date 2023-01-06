package com.demo.modules.main

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.modules.monitor.FpsMonitor
import kotlinx.coroutines.launch

/**
 * Description
 * Author henry
 * Date   2022/12/29
 */
class MainViewModel : ViewModel() {

    private val _fps: MutableLiveData<Int> = MutableLiveData()
    val fps: LiveData<Int> get() = _fps

    private var fpsMonitorThread: HandlerThread? = null
    private var fpsMonitor: FpsMonitor = FpsMonitor()

    fun watchFps() {
        Looper.getMainLooper()
        viewModelScope.launch {
            fpsMonitorThread = HandlerThread("fps_monitor")
            fpsMonitorThread?.run {
                start()
                val fpsMonitorHandler = Handler(looper)
                fpsMonitorHandler.post {
                    with(fpsMonitor) {
                        callback = { _fps.postValue(it) }
                        start()
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fpsMonitor.stop()
        fpsMonitorThread?.quitSafely()
    }

}
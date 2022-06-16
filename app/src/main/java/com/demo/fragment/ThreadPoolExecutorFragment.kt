package com.demo.fragment

import android.os.*
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.demo.one.R
import java.lang.ref.WeakReference
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by hengwei on 2021/4/7.
 */
class ThreadPoolExecutorFragment : Fragment() {

    companion object {
        const val TAG = "ExecutorServiceActivity"
        const val MSG_UPDATE_SUM = 0
        const val MSG_UPDATE_LOGCAT = 1

        private class NoLeakHandler(activity: ThreadPoolExecutorFragment) :
            Handler(Looper.getMainLooper()) {
            private var ref: WeakReference<ThreadPoolExecutorFragment> = WeakReference(activity)
            override fun handleMessage(msg: Message) {
                msg.run {
                    val text = obj as String
                    val activity = ref.get()
                    if (what == MSG_UPDATE_SUM) {
                        activity?.tvSum?.text = text
                        return
                    }
                    if (what == MSG_UPDATE_LOGCAT) {
                        activity?.logcat?.append(text)?.append("\r\n")
                        activity?.tvLogcat?.text = activity?.logcat
                    }
                }
            }
        }
    }

    private lateinit var root: View
    private val llBtnContainer: LinearLayout by lazy { root.findViewById(R.id.ll_btn_container) }
    private val tvSum: TextView by lazy { root.findViewById(R.id.tv_sum) }
    private val tvLogcat: TextView by lazy { root.findViewById(R.id.tv_logcat) }

    private var currentService: ThreadPoolExecutor? = null

    private val handler: NoLeakHandler by lazy { NoLeakHandler(this) }
    private var monitorHandler: Handler? = null
    private var handlerThread = HandlerThread("monitor_thread")
    private lateinit var monitorRunnable: Runnable //监控任务

    private val logcat = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handlerThread.start()
        monitorHandler = Handler(handlerThread.looper)

        monitorRunnable = object : Runnable {
            override fun run() {

                //监控线程的结束的标志是线程池里没有正在运行的线程
                val end = (currentService?.activeCount ?: 0) <= 0

                currentService?.run {
                    val msg =
                        "核心线程: $corePoolSize 最大线程: $maximumPoolSize 正在运行线程 : $activeCount 等待队列 : ${queue.size}"

                    Message.obtain().run {
                        what = MSG_UPDATE_SUM
                        obj = msg
                        handler.sendMessage(this)
                    }

                    Log.i(TAG, msg)
                }

                if (end) {
                    handlerThread.quit()
                    return
                }
                //每50ms查询一次线程池状态
                monitorHandler?.postDelayed(this, 50)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        root = inflater.inflate(R.layout.fragment_thread_pool, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvLogcat.movementMethod = ScrollingMovementMethod.getInstance()
        addService("SingleThreadExecutor", newSingleThreadExecutor())
        addService("FixedThreadPool(5)", newFixedThreadPool(5))
        addService("CachedThreadPool", newCachedThreadPool())
    }

    private fun addService(text: String, executorService: ThreadPoolExecutor) {
        Button(context).also {
            it.text = text
            it.setOnClickListener {
                start(executorService)
            }
            llBtnContainer.addView(it)
        }
    }

    private fun start(executorService: ThreadPoolExecutor) {
        startExecute(executorService)
        startMonitor()
    }

    /**
     * 启动监控线程
     */
    private fun startMonitor() {
        monitorHandler?.post(monitorRunnable)
    }

    private fun startExecute(executorService: ThreadPoolExecutor) {
        currentService = executorService
        for (i in 1..50) {
            executorService.execute {
                val msg = Thread.currentThread().name + " " + i
                Message.obtain().run {
                    what = MSG_UPDATE_LOGCAT
                    obj = msg
                    handler.sendMessage(this)
                }
                Log.d(TAG, msg)
                Thread.sleep(1000)
            }
        }
    }

    private fun newSingleThreadExecutor(): ThreadPoolExecutor {
        return ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    }

    private fun newFixedThreadPool(nThreads: Int): ThreadPoolExecutor {
        return ThreadPoolExecutor(nThreads, nThreads, 0L,
            TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    }

    private fun newCachedThreadPool(): ThreadPoolExecutor {
        return ThreadPoolExecutor(0, Int.MAX_VALUE, 60L,
            TimeUnit.SECONDS, SynchronousQueue())
    }

}
package com.demo.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Description 协程工具类
 * Author wanghengwei
 * Date   2019/3/22 20:12
 */
object CoroutinesUtil {

    /**
     * 基本方法，
     * 主线程打印"Hello",然后阻塞两秒;
     * 非阻塞线程延时一秒后打印"World";
     * 主线程阻塞两秒后打印"End"
     */
    fun basicMethod() {
        GlobalScope.launch {
            delay(1000) // delay 是一个特殊的 挂起函数 ，它不会造成线程阻塞，但是会 挂起 协程，并且只能在协程中使用
            LogUtil.printCoroutines("World")
        }
        LogUtil.printCoroutines("Hello")
        Thread.sleep(2000) // 延迟 2 秒来保证 JVM 存活
        LogUtil.printCoroutines("End")
    }

    /**
     *与 basicMethod 方法区别为 ：runBlocking 替换了 Thread.sleep
     */
    fun basicMethod2() {
        GlobalScope.launch {
            delay(1000)
            LogUtil.printCoroutines("World")
        }
        LogUtil.printCoroutines("Hello")
        // 这个表达式阻塞了主线程
        runBlocking {
            delay(2000)
            LogUtil.printCoroutines("End")
        }
    }

    /**
     * 使用join 方法代替 Thread.sleep 或者 delay 来保证JVM存活
     */
    fun basicMethod3() = runBlocking {
        // 启动一个新协程并保持对这个作业的引用
        val job = GlobalScope.launch {
            delay(1000)
            LogUtil.printCoroutines("World")
        }
        LogUtil.printCoroutines("Hello")
        // 等待直到子协程执行结束
        job.join()
        LogUtil.printCoroutines("End")
    }

}
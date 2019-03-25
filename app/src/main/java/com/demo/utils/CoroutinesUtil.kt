package com.demo.utils

import kotlinx.coroutines.*

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

    /**
     * cancel()方法示例
     */
    fun cancelMethod() = runBlocking {
        val job = GlobalScope.launch {
            repeat(50) { i ->
                LogUtil.printCoroutines("I'm sleeping $i ……")
                delay(500)
            }
        }
        delay(1300)// 延迟一段时间
        LogUtil.printCoroutines("main: I'm tired of waiting!")
        job.cancelAndJoin()
        LogUtil.printCoroutines("main: Now I can quit.")
    }

    /**
     * cancel()方法示例
     * 由于while(i<10)循环里没有挂起函数（delay），所以cancelAndJoin不能及时取消协程。
     * 但是把 while(i<10) 替换为 while(isActive) ,就能够及时取消协程
     * isActive 是一个可以被使用在 CoroutineScope 中的扩展属性。
     */
    fun cancelMethod2() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 10) { // 一个执行计算的循环，只是为了占用 CPU
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    LogUtil.printCoroutines("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 等待一段时间
        LogUtil.printCoroutines("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消一个任务并且等待它结束
        LogUtil.printCoroutines("main: Now I can quit.")
    }

    /**
     * 协程超时情况示例
     * 如果使用 withTimeOut方法，则需要用 try catch代码块包裹
     * 使用 withTimeoutOrNull 不需要 try catch 代码块包裹
     */
    fun timeoutMethod() = runBlocking {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                LogUtil.printCoroutines("I'm sleeping $i ...")
                delay(500L)
            }
        }
        LogUtil.printCoroutines("Result is $result")
    }

}
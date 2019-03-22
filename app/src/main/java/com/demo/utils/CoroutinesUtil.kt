package com.demo.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        Thread.sleep(2000)
        LogUtil.printCoroutines("End")
    }

}
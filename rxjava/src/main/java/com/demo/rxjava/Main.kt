package com.demo.rxjava

/**
 * 结论一：concat操作符组合的两个Observable运行的线程为单独创建两个Observable时subscribeOn的线程，
 *        而不是组合后subscribeOn的线程。观察者运行的线程为组合后observeOn的线程。
 */
public class Main {
}

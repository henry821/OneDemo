package com.demo.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Description
 * <p>题目描述：
 * <p>  两个线程同时对volatile修饰的num++ 10000次，结果如何。
 * <p>期望结果：
 * <p>  输出20000
 * <p>实际结果：
 * <p>  可能小于20000
 * <p>原因：
 * <p>  因为num++不是个原子性的操作，而是个复合操作，需要三步，读取、加一、赋值。
 * <p>  所以，在多线程环境下，有可能线程A将num读取到本地内存中，此时其他线程可能已经将num增大了很多，线程A依然对过期的num进行自加，重新写到主存中。
 * <p>  最终导致了num的结果不合预期，而是小于20000。
 * Author henry
 * Date   2023/2/21
 */
public class Volatile1 {
    public static final int THREAD_COUNT = 2;
    public static volatile int num = 0;
    //使用CountDownLatch来等待计算线程执行完
    static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        //开启30个线程进行累加操作
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        num++;//自加操作
                    }
                    countDownLatch.countDown();
                }
            }.start();
        }
        //等待计算线程执行完
        countDownLatch.await();
        System.out.println(num);
    }
}

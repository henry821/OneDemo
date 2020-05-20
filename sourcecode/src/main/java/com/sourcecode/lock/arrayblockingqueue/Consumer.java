package com.sourcecode.lock.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Description 消费者线程
 * Author wanghengwei
 * Date   2020/5/20
 */
public class Consumer implements Runnable {

    private ArrayBlockingQueue<Apple> queue;

    Consumer(ArrayBlockingQueue<Apple> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("消费线程启动");
        try {
            for (int i = 0; i < 3; i++) {
                //取出苹果
                Apple apple;
                apple = queue.take();
                System.out.println("消费：" + apple);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

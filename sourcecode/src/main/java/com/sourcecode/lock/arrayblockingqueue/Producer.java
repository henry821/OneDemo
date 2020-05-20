package com.sourcecode.lock.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Description 生产者线程
 * Author wanghengwei
 * Date   2020/5/20
 */
public class Producer implements Runnable {

    private ArrayBlockingQueue<Apple> queue;

    public Producer(ArrayBlockingQueue<Apple> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("生产线程启动");
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(2000);
                //生产苹果，放入队列
                Apple apple = new Apple();
                queue.put(apple);
                System.out.println("生产：" + apple);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

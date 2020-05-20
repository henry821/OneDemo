package com.sourcecode.lock.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Description
 * Author wanghengwei
 * Date   2020/5/20
 */
public class Test {

    public static void main(String[] args) {

        ArrayBlockingQueue<Apple> apples = new ArrayBlockingQueue<>(1);

        new Thread(new Producer(apples)).start();
        new Thread(new Consumer(apples)).start();
    }

}

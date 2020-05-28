package com.demo.rxjava.transform;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 定期从 被观察者（Observable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Buffer {

    public static void main(String[] args) {

        Observable
                .just(1, 2, 3, 4, 5)
                .buffer(3, 1) //设置缓存区大小&步长，缓存区大小 = 每次从被观察者中获取的事件数量，步长 = 每次获取新事件的数量
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

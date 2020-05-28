package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Description 每隔指定时间 就发送 事件，可指定发送的数据的数量
 * 作用类似于interval（），但可指定发送的数据的数量
 * Author wanghengwei
 * Date   2020/5/28
 */
public class IntervalRange {

    public static void main(String[] args) {

        // 1. 从3开始，一共发送10个事件；
        // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字`
        Observable
                .intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

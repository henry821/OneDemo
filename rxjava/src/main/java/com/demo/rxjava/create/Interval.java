package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Description 每隔指定时间 就发送 事件，发送的事件序列 = 从0开始、无限递增1的的整数序列
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Interval {

    public static void main(String[] args) {

        //延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
        Observable
                .interval(3, 1, TimeUnit.SECONDS)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

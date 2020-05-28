package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Description 延迟指定时间后，调用一次 onNext(0)
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Timer {

    public static void main(String[] args) {

        Observable
                .timer(2, TimeUnit.SECONDS)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

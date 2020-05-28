package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 统计被观察者发送事件的数量
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Count {

    public static void main(String[] args) {

        Observable
                .just(1, 2, 3, 4)
                .count()
                .subscribe(ObserverUtil.getBasicSingleObserver());

    }

}

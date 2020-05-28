package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
 * Author wanghengwei
 * Date   2020/5/28
 */
public class StartWith {

    public static void main(String[] args) {

        //追加数据顺序 = 后调用先追加
        Observable
                .just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

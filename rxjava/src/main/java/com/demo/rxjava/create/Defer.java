package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Description 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Defer {

    //第一次赋值
    private static Integer i = 10;

    public static void main(String[] args) {

        //通过defer定义被观察者对象
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        //第二次赋值
        i = 15;

        //观察者开始订阅，此时才会调用defer()创建被观察者对象（Observable）
        //因为是在订阅时才创建，所以i会取第二次的赋值
        observable.subscribe(ObserverUtil.getBasicObserver());

    }

}

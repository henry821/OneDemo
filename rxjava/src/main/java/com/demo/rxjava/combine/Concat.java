package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description concat() / concatArray()
 * 组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
 * 二者区别：组合被观察者的数量，即concat()组合被观察者数量≤4个，而concatArray()则可＞4个
 * Author wanghengwei
 * Date   2020/5/12
 */
public class Concat {

    public static void main(String[] args) {

        Observable<String> just1 = Observable.just("just1--1", "just1--2");
        Observable<String> just2 = Observable.just("just2--1", "just2--2");
        Observable<String> just3 = Observable.just("just3--1", "just3--2");
        Observable<String> just4 = Observable.just("just4--1", "just4--2");
        Observable<String> just5 = Observable.just("just5--1", "just5--2");

        Observable.concat(just1, just2, just3, just4)
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("----------------------------------------");

        Observable.concatArray(just1, just2, just3, just4, just5)
                .subscribe(ObserverUtil.getBasicObserver());
    }


}

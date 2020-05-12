package com.demo.rxjava;

import com.demo.rxjava.utl.ObservableUtil;
import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送
 * Author wanghengwei
 * Date   2020/5/12
 */
public class Zip {

    public static void main(String[] args) {

        Observable<String> delay1 = ObservableUtil.createDelayObservable(1, 2500);
        Observable<String> delay2 = ObservableUtil.createDelayObservable(4, 1000);

        Observable.zip(delay1, delay2, (s, s2) -> s + " ^^^^ " + s2)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

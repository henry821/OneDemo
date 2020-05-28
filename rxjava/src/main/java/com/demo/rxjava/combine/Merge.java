package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObservableUtil;
import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description merge() / mergeArray()
 * 组合多个被观察者一起发送数据，合并后 按时间线并行执行
 * 组合被观察者的数量，即merge()组合被观察者数量≤4个，而mergeArray()则可＞4个
 * Author wanghengwei
 * Date   2020/5/12
 */
public class Merge {

    public static void main(String[] args) {

        Observable<String> delay1 = ObservableUtil.createDelayObservable(1, 2500);
        Observable<String> delay2 = ObservableUtil.createDelayObservable(2, 2000);
        Observable<String> delay3 = ObservableUtil.createDelayObservable(3, 1500);
        Observable<String> delay4 = ObservableUtil.createDelayObservable(4, 1000);

        Observable.merge(delay1, delay2, delay3, delay4)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

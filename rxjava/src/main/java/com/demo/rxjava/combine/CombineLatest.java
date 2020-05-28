package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObservableUtil;
import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
 * 与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
 * Author wanghengwei
 * Date   2020/5/12
 */
public class CombineLatest {

    public static void main(String[] args) {
        Observable
                .combineLatest(
                        Observable.just(1L, 2L, 3L),
                        ObservableUtil.createDelayObservable(1, 1000),
                        (aLong, s) -> {
                            System.out.println("开始合并");
                            return aLong + " 合并 " + s;
                        })
                .subscribe(ObserverUtil.getBasicObserver());

    }
}

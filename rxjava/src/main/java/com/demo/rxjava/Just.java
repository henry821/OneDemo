package com.demo.rxjava;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description Just操作符
 * Author wanghengwei
 * Date   2020/5/12
 */
public class Just {

    public static void main(String[] args) {
        Observable.just("just操作符")
                .subscribe(ObserverUtil.getBasicObserver());
    }

}

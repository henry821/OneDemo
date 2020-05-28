package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description
 * Author wanghengwei
 * Date   2020/5/12
 */
public class FromArray {

    public static void main(String[] args) {

        String[] strings = {"String1", "String2", "String3"};

        Observable.fromArray(strings)
                .subscribe(ObserverUtil.getBasicObserver());
    }
}

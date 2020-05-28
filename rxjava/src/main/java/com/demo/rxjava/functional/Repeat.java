package com.demo.rxjava.functional;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 重复不断地发送被观察者事件
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Repeat {

    public static void main(String[] args) {

        Observable
                .just(1, 2, 3, 4, 5)
                .repeat(3) // 重复创建次数 =- 3次
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

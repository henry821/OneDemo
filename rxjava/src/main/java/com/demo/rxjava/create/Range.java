package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;

/**
 * Description 连续发送 1个事件序列，可指定范围
 * 作用类似于intervalRange（），但区别在于：无延迟发送事件
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Range {

    public static void main(String[] args) {

        //从3开始发送，每次发送事件递增1，一共发送10个事件
        Observable
                .range(3, 10)
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("-------------------------------------");

        //类似于range（），区别在于该方法支持数据类型 = Long
        Observable
                .rangeLong(3, 10)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

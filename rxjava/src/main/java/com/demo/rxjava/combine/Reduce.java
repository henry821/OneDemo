package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

/**
 * Description 把被观察者需要发送的事件聚合成1个事件 & 发送
 * 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Reduce {

    public static void main(String[] args) {

        Observable
                .just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer s1, Integer s2) throws Exception {
                        System.out.println(String.format("本次计算的数据是：%s * %s", s1, s2));
                        return s1 * s2;
                    }
                })
                .subscribe(ObserverUtil.getBasicMaybeObserver());


    }

}

package com.demo.rxjava.combine;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;

/**
 * Description 将被观察者Observable发送的数据事件收集到一个数据结构里
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Collect {

    public static void main(String[] args) {

        Observable
                .just(1, 2, 3, 4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() { //1.创建数据结构（容器），用于收集被观察者发送的数据
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() { //2.对发送的数据进行收集
                    @Override
                    public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                        //list = 容器，integer = 后者数据
                        //对发送的数据进行收集
                        list.add(integer);
                    }
                })
                .subscribe(ObserverUtil.getBasicSingleObserver());

    }

}

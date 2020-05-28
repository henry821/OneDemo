package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Description 直接发送 传入的集合List数据，会将数组中的数据转换为Observable对象
 * Author wanghengwei
 * Date   2020/5/28
 */
public class FromIterable {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Observable
                .fromIterable(list)
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

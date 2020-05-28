package com.demo.rxjava.transform;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Description Map操作符，将被观察者发送的事件转换为任意的类型事件。
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Map {

    public static void main(String[] args) {

        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.format("使用 Map变换操作符 将整型 %s 转变成字符串 %s ", integer,
                                String.valueOf(integer));
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

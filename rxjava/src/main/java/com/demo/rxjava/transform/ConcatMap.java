package com.demo.rxjava.transform;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description 类似FlatMap（）操作符
 * 与FlatMap（）的 区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
 * Author wanghengwei
 * Date   2020/5/28
 */
public class ConcatMap {

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
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(String.format("我是事件 %s 拆分后的子事件 %s", integer, i));
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());
    }

}

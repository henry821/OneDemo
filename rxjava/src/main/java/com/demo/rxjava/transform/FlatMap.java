package com.demo.rxjava.transform;

import com.demo.rxjava.utl.ObserverUtil;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
 * Author wanghengwei
 * Date   2020/5/28
 */
public class FlatMap {

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
                .flatMap(new Function<Integer, ObservableSource<String>>() {
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

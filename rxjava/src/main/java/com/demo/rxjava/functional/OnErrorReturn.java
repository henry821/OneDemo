package com.demo.rxjava.functional;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Description 遇到错误时，发送1个特殊事件 & 正常终止
 * 可捕获在它之前发生的异常
 * Author wanghengwei
 * Date   2020/5/28
 */
public class OnErrorReturn {

    public static void main(String[] args) {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new Throwable("发生错误了"));
                    }
                })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        System.out.println(String.format("在onErrorReturn处理了错误：%s", throwable.getMessage()));
                        return 666;
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());
    }

}

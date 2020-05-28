package com.demo.rxjava.functional;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BooleanSupplier;

/**
 * Description 出现错误后，判断是否需要重新发送数据
 * 若需要重新发送 & 持续遇到错误，则持续重试
 * 作用类似于retry（Predicate predicate），唯一区别：返回 true 则不重新发送数据事件
 * Author wanghengwei
 * Date   2020/5/28
 */
public class RetryUntil {

    public static void main(String[] args) {

        basicObservable()
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        //true --> 不重发并结束，false --> 重发
                        return true;
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());

    }

    private static Observable<Integer> basicObservable() {
        return Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onError(new Exception("发生错误了"));
                        emitter.onNext(3);
                    }
                });
    }

}

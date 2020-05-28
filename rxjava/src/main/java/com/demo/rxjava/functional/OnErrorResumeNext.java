package com.demo.rxjava.functional;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description 遇到错误时，发送1个新的Observable
 * onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
 * 若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法
 * Author wanghengwei
 * Date   2020/5/28
 */
public class OnErrorResumeNext {

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
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        //捕捉错误异常
                        System.out.println(String.format("在onErrorReturn处理了错误：%s", throwable.getMessage()));
                        //发生错误事件后，发送一个新的被观察者&发送事件序列
                        return Observable.just(11, 22);
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());

    }

}

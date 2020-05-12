package com.demo.rxjava.utl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description 获取Observer
 * Author wanghengwei
 * Date   2020/5/12
 */
public class ObserverUtil {

    public static <T> Observer<T> getBasicObserver() {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxJavaLogUtil.printOnSubscribe();
            }

            @Override
            public void onNext(T t) {
                RxJavaLogUtil.printOnNext(t);
            }

            @Override
            public void onError(Throwable e) {
                RxJavaLogUtil.printOnError(e);
            }

            @Override
            public void onComplete() {
                RxJavaLogUtil.printOnComplete();
            }
        };
    }

}

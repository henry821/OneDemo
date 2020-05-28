package com.demo.rxjava.create;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Description 创建型操作符
 * Author wanghengwei
 * Date   2020/3/24
 */
public class Create {

    public static void main(String[] args) {

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("发射一个字符串");
            emitter.onComplete();
        }).subscribe(ObserverUtil.getBasicObserver());

    }

}

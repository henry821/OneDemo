package com.demo.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description 创建型操作符
 * Author wanghengwei
 * Date   2020/3/24
 */
public class CreateOperators {

    private static final String TAG = "CreateOperators";

    private Observer<String> commonObserver;
    private String[] stringArray;

    private CreateOperators() {
        stringArray = new String[]{"String1", "String2", "String3"};
        commonObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                printOnSubscribe();
            }

            @Override
            public void onNext(String s) {
                printOnNext(s);
            }

            @Override
            public void onError(Throwable e) {
                printOnError(e);
            }

            @Override
            public void onComplete() {
                printOnComplete();
            }
        };
    }

    public static void main(String[] args) {

        CreateOperators createOperators = new CreateOperators();

        createOperators.create();
        createOperators.just();
        createOperators.fromArray();


    }

    private void create() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("发射一个字符串");
                emitter.onComplete();
            }
        }).subscribe(commonObserver);
    }

    private void just() {
        Observable.just("just操作符").subscribe(commonObserver);
    }

    private void fromArray() {
        Observable.fromArray(stringArray).subscribe(commonObserver);
    }

    private void printLog(String content) {
        String log = String.format("%s --> 当前线程：%s", content, Thread.currentThread().getName());
        System.out.println(log);
    }

    private void printOnSubscribe() {
        printLog("onSubscribe");
    }

    private void printOnNext(String content) {
        printLog("onNext : " + content);
    }

    private void printOnComplete() {
        printLog("onComplete");
    }

    private void printOnError(Throwable throwable) {
        printLog("onError : " + throwable.getMessage());
    }

}

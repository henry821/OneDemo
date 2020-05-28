package com.demo.rxjava.functional;

import com.demo.rxjava.utl.ObserverUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;

/**
 * Description 重试，即当出现错误时，让被观察者（Observable）重新发射数据
 * Author wanghengwei
 * Date   2020/5/28
 */
public class Retry {

    public static void main(String[] args) {

        basicObservable()
                .retry() //遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送）
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("-----------------------------------------------------------");

        basicObservable()
                .retry(3) //设置重试次数 = 3次
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("-----------------------------------------------------------");

        basicObservable()
                .retry(new Predicate<Throwable>() {  // 拦截错误后，判断是否需要重新发送请求
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        System.out.println(String.format("retry错误：%s", throwable.toString()));
                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("-----------------------------------------------------------");

        basicObservable()
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(Integer integer, Throwable throwable) throws Exception {
                        System.out.println(String.format("异常错误 = %s", throwable.toString()));
                        System.out.println(String.format("当前重试次数 = %s", integer));
                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(ObserverUtil.getBasicObserver());

        System.out.println("-----------------------------------------------------------");

        basicObservable()
                .retry(3, new Predicate<Throwable>() { // 拦截错误后，判断是否需要重新发送请求
                    @Override
                    public boolean test(Throwable throwable) throws Exception {
                        // 捕获异常
                        System.out.println(String.format("retry错误：%s", throwable.toString()));
                        //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                        //返回true = 重新发送请求（最多重新发送3次）
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

package com.demo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.demo.one.base.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 学习RxJava源码，基于2.2.7
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 11:37
 */
public class RxJavaFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        LogUtil.e("subscribe" + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.e("doOnNext : " + s + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e("onSubscribe" + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.e("onNext : " + s + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onError" + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e("onComplete" + "(当前线程 ：" + Thread.currentThread().getName() + ")");
                    }
                });
    }
}

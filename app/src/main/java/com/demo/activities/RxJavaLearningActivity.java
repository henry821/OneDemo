package com.demo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.demo.one.R;
import com.demo.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description 学习RxJava源码，基于2.2.7
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 11:37
 */
public class RxJavaLearningActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaLearningActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_learning);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
                emitter.onNext("World");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                LogUtil.e(TAG, "onNext : " + s);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG, "onComplete");
            }
        });
    }
}

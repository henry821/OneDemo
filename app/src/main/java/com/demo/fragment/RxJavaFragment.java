package com.demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.demo.one.R;
import com.demo.one.databinding.FragmentRxjavaBinding;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 学习RxJava源码，基于2.2.7
 * <p>
 * 结论一：concat操作符组合的两个Observable运行的线程为单独创建两个Observable时subscribeOn的线程，
 *        而不是组合后subscribeOn的线程。观察者运行的线程为组合后observeOn的线程。
 *
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 11:37
 */
public class RxJavaFragment extends BaseFragment {

    private static final String TAG = "RxJavaFragment";

    private Disposable disposable;

    private Observable<Integer> observable1;
    private Observable<Integer> observable2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observable1 = Observable.just(1, 2, 3, 4)
                .subscribeOn(AndroidSchedulers.mainThread());
        observable2 = Observable.just(5, 6, 7, 8)
                .subscribeOn(Schedulers.io());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRxjavaBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rxjava, container, false);
        binding.setOnClickListener(this);
        return binding.getRoot();
    }

    public void concat() {
        Observable.concat(observable1, observable2)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        Log.e(TAG, "onSubscribe:");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer + " " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }
}

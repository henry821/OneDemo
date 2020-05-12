package com.demo.rxjava.utl;

import io.reactivex.Observable;

/**
 * Description
 * Author wanghengwei
 * Date   2020/5/12
 */
public class ObservableUtil {

    public static Observable<String> createDelayObservable(int index, long delay) {
        return Observable.create(emitter -> {
            emitter.onNext(String.format("第%s个任务开始", index));
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    emitter.onNext(String.format("第 %s 个任务完成，耗时%s ", index, delay));
                }
            }.start();
        });
    }

}

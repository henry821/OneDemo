package com.demo.utils;

import android.util.Log;

/**
 * Description 输出简单Log工具类，输出详细Log信息使用{@link com.orhanobut.logger.Logger}
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 14:22
 */
public class LogUtil {

    private static final String TAG = "LogUtil";

    public static void v(String content) {
        Log.v(TAG, content);
    }

    public static void d(String content) {
        Log.d(TAG, content);
    }

    public static void i(String content) {
        Log.i(TAG, content);
    }

    public static void w(String content) {
        Log.w(TAG, content);
    }

    public static void e(String content) {
        Log.e(TAG, content);
    }


    /********************** 具体情境打印Log方法 *************************/

    public static void printObservable(Class clazz, String content) {
        v("Observable : " + clazz.getSimpleName() + "." + content + "(当前线程：" + Thread.currentThread().getName() + ")");
    }

    public static void printObserver(Class clazz, String content) {
        d("Observer : " + clazz.getSimpleName() + "." + content + "(当前线程：" + Thread.currentThread().getName() + ")");
    }

    public static void printEmitter(Class clazz, String content) {
        i("Emitter : " + clazz.getSimpleName() + "." + content + "(当前线程：" + Thread.currentThread().getName() + ")");
    }
}

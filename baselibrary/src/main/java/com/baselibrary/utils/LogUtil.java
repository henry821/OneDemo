package com.baselibrary.utils;

import android.util.Log;

/**
 * Description 输出简单Log工具类
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

    public static void printCoroutines(String content) {
        String wrappedContent = content + " (当前线程" + Thread.currentThread().getName() + ")";
        LogUtil.e(wrappedContent);
    }

    public static void printError(Throwable throwable) {
        Log.e(TAG, "error: ", throwable);
    }

    public static void printClassLoaderD(String content) {
        d("[类加载器学习]" + content);
    }

    public static void printClassLoaderE(String content) {
        e("[类加载器学习]" + content);
    }
}

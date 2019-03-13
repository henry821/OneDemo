package com.demo.utils;

import android.util.Log;

/**
 * Description 输出简单Log工具类，输出详细Log信息使用{@link com.orhanobut.logger.Logger}
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 14:22
 */
public class LogUtil {

    private static final String TAG = "LogUtil";

    public static void d(String content) {
        Log.d(TAG, content);
    }

    public static void v(String content) {
        Log.v(TAG, content);
    }

    public static void i(String content) {
        Log.i(TAG, content);
    }

    public static void e(String content) {
        Log.e(TAG, content);
    }

    public static void w(String content) {
        Log.w(TAG, content);
    }
}

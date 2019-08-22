package com.demo.beans;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Description 模拟内存泄漏的单例类
 * Author wanghengwei
 * Date   2019/8/22 16:25
 */
public class MemoryLeakSingleton {

    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    private static MemoryLeakSingleton INSTANCE;

    /**
     * 把一个Activity设置给mContext。所以mContext持有了Activity的引用
     * 由于此类是一个单例类，其生命周期 = 应用程序生命周期
     * 所以Activity无法被回收，从而出现内存泄漏
     *
     * @param context Activity类型Context
     */
    private MemoryLeakSingleton(Context context) {
        mContext = context;
    }

    public static MemoryLeakSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MemoryLeakSingleton(context);
        }
        return INSTANCE;
    }


}

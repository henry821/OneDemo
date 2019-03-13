package com.demo.application;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Description 主Application
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 11:57
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}

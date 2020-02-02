package com.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.demo.one.base.utils.LogUtil;

/**
 * Description
 * Author wanghengwei
 * Date   2019/8/28 19:31
 */
public abstract class BaseService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.w(getClass().getName() + "---onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.w(getClass().getName() + "---onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.w(getClass().getName() + "---onBind");
        return getBinder(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.w(getClass().getName() + "---onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.w(getClass().getName() + "---onDestroy");
        super.onDestroy();
    }

    abstract IBinder getBinder(Intent intent);
}

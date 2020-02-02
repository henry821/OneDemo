package com.demo.service;

import android.content.Intent;
import android.os.IBinder;

import com.demo.one.base.utils.LogUtil;

/**
 * Description 使用start方法创建的Service
 * Author wanghengwei
 * Date   2019/8/28 19:29
 */
public class StartModeService extends BaseService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(getClass().getName() + "--开始执行耗时操作--" + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    IBinder getBinder(Intent intent) {
        return null;
    }
}

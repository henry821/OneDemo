package com.demo.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.baselibrary.utils.LogUtil;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
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
        Stetho.initializeWithDefaults(this);
        BlockCanary.install(this, new BlockCanaryContext()).start();
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallback());
    }

    private class ActivityLifeCycleCallback implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogUtil.v(activity.getLocalClassName() + "---onCreate,Bundle : "
                    + (savedInstanceState == null ? "null" : savedInstanceState.toString()));
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogUtil.v(activity.getLocalClassName() + "---onStart");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUtil.v(activity.getLocalClassName() + "---onResume");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtil.v(activity.getLocalClassName() + "---onPause");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogUtil.v(activity.getLocalClassName() + "---onStop");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtil.v(activity.getLocalClassName() + "--onSaveInstanceState,Bundle : "
                    + (outState == null ? "null" : outState.toString()));
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtil.v(activity.getLocalClassName() + "---onDestroy");
        }
    }

}

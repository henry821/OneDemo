package com.demo.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.baselibrary.utils.LogUtil;
import com.demo.other.matrix.DynamicConfigImplDemo;
import com.demo.other.matrix.TestPluginListener;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;

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
        initMatrix();
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallback());
    }

    private void initMatrix() {
        Matrix.Builder builder = new Matrix.Builder(this); // build matrix
        builder.patchListener(new TestPluginListener(this)); // add general pluginListener
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo(); // dynamic config

        // init plugin
        IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
        //add to matrix
        builder.plugin(ioCanaryPlugin);

        //init matrix
        Matrix.init(builder.build());

        // start plugin
        ioCanaryPlugin.start();
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

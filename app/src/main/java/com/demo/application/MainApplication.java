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
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.threadcanary.ThreadConfig;
import com.tencent.matrix.threadcanary.ThreadWatcher;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.sqlitelint.SQLiteLint;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;

/**
 * Description 主Application
 * <br>Author wanghengwei
 * <br>Date   2019/3/13 11:57
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        Stetho.initializeWithDefaults(this);
        initMatrix();
        registerActivityLifecycleCallbacks(new ActivityLifeCycleCallback());
    }

    private void initMatrix() {
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        MatrixLog.i(TAG, "MatrixApplication.onCreate");

        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

        if (matrixEnable) {

            //resource
            builder.plugin(new ResourcePlugin(new ResourceConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .setDumpHprof(false)
                    .setDetectDebuger(true)     //only set true when in sample, not in your app
                    .build()));
            ResourcePlugin.activityLeakFixer(this);

            //io
            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .build());
            builder.plugin(ioCanaryPlugin);


            // prevent api 19 UnsatisfiedLinkError
            //sqlite
            SQLiteLintConfig config = initSQLiteLintConfig();
            SQLiteLintPlugin sqLiteLintPlugin = new SQLiteLintPlugin(config);
            builder.plugin(sqLiteLintPlugin);

            ThreadWatcher threadWatcher = new ThreadWatcher(new ThreadConfig.Builder().dynamicConfig(dynamicConfig).build());
            builder.plugin(threadWatcher);

        }

        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
        Matrix.with().getPluginByClass(ThreadWatcher.class).start();
        MatrixLog.i("Matrix.HackCallback", "end:%s", System.currentTimeMillis());
    }

    private static SQLiteLintConfig initSQLiteLintConfig() {
        try {
            /**
             * HOOK模式下，SQLiteLint会自己去获取所有已执行的sql语句及其耗时(by hooking sqlite3_profile)
             * @see 而另一个模式：SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY , 则需要调用 {@link SQLiteLint#notifySqlExecution(String, String, int)}来通知
             * SQLiteLint 需要分析的、已执行的sql语句及其耗时
             * @see TestSQLiteLintActivity#doTest()
             */
            return new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);
        } catch (Throwable t) {
            return new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);
        }
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

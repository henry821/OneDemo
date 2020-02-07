package com.demo.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import static com.demo.constant.LogConstant.TAG_LIFECYCLE;

/**
 * Description Fragment基类,实现{@link LifecycleObserver}
 * Author wanghengwei
 * Date   2020/2/7
 */
public class BaseFragment extends Fragment implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void printOnCreateLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void printOnStartLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void printOnResumeLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void printOnPauseLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void printOnStopLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void printOnDestroyLifeCycle() {
        Log.e(TAG_LIFECYCLE, getClass().getSimpleName() + " onDestroy");
    }

}

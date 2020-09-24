package com.demo.autoplay;

import android.app.Fragment;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import static com.demo.autoplay.AutoPlayManager.AUTO_PLAY_ITEM;
import static com.demo.autoplay.AutoPlayManager.EXPOSURE_ITEM;

/**
 * 使用一个嵌入的Fragment管理检测的时机，控制自动播放
 * Created by kaige1 on 07/09/2017.
 */
public class AutoPlaybackFragment extends Fragment {

    //多标签页面可能会有多个ViewGroup检测
    private SparseArray<ViewVisibilityDetector> mDetectors = new SparseArray<>(1);

    private Handler mHandler = new Handler();

    static AutoPlaybackFragment create() {
        return new AutoPlaybackFragment();
    }

    void bind(ViewGroup targetViewGroup, int topOffset, int bottomOffset, boolean exposure) {
        if (targetViewGroup == null) {
            return;
        }

        final int key = keyForView(targetViewGroup);
        ViewVisibilityDetector detector = mDetectors.get(key);
        if (detector == null) {
            detector = DetectorFactory.makeDetector(targetViewGroup, topOffset, bottomOffset);
            detector.addTarget(AUTO_PLAY_ITEM); // 自动播放
            if (exposure) {
                detector.addTarget(EXPOSURE_ITEM); // 曝光
            }
            mDetectors.put(key, detector);
        }

        detector.start(); // 绑定后立即进行一次检测
    }

    private int keyForView(View view) {
        return view != null ? view.hashCode() : 0;
    }

    /**
     * 手动检测，并激活item
     *
     * @param targetView
     */
    void activate(ViewGroup targetView) {
        if (mDetectors == null || targetView == null) {
            return;
        }

        final int detectorKey = keyForView(targetView);
        for (int i = 0; i < mDetectors.size(); i++) {
            final int key = mDetectors.keyAt(i);
            final ViewVisibilityDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                if (detectorKey == key) {
                    detector.start();
                } else {
                    detector.stop();
                }
            }
        }
    }

    /**
     * 延迟检测
     *
     * @param targetView
     * @param delay
     */
    void activateDelay(ViewGroup targetView, long delay) {
        if (targetView != null && delay > 0) {
            mHandler.postDelayed(new ActivateAction(this, targetView), delay);
        }
    }

    /**
     * 手动停止检测
     *
     * @param targetView
     */
    void deactivate(ViewGroup targetView) {
        if (mDetectors != null && targetView != null) {
            final int detectorKey = keyForView(targetView);
            ViewVisibilityDetector detector = mDetectors.get(detectorKey);
            if (detector != null) {
                detector.stop();
            }
            mDetectors.remove(detectorKey);
        }
    }

    /**
     * 手动检测，并激活item
     */
    void activateAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final ViewVisibilityDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.start();
            }
        }
    }

    /**
     * 暂停检测
     */
    void pauseAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final ViewVisibilityDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.pause();
            }
        }
    }

    /**
     * 恢复检测
     */
    void resumeAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final ViewVisibilityDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.resume();
            }
        }
    }

    /**
     * 手动检测活跃的item并反激活
     */
    void deactivateAll() {
        if (mDetectors == null) {
            return;
        }

        for (int i = 0; i < mDetectors.size(); i++) {
            final ViewVisibilityDetector detector = mDetectors.valueAt(i);
            if (detector != null) {
                detector.stop();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activateAll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
        deactivateAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetectors.clear();
    }

    private static class ActivateAction implements Runnable {

        private WeakReference<ViewGroup> wrTarget;
        private WeakReference<AutoPlaybackFragment> wrHost;

        public ActivateAction(AutoPlaybackFragment host, ViewGroup target) {
            this.wrHost = new WeakReference<>(host);
            this.wrTarget = new WeakReference<>(target);
        }

        @Override
        public void run() {
            AutoPlaybackFragment host = wrHost.get();
            ViewGroup target = wrTarget.get();
            if (host != null && target != null) {
                host.activate(target);
            }
        }
    }

}

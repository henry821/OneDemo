package com.demo.autoplay;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.ViewGroup;

/**
 * 自动播放，典型用法：
 * AutoPlayManager.bind(ListView).topOffset(TitleBarHeight).bottomOffset(ListView.height - TabBar.Height).detect()
 */
public class AutoPlayManager {

    public static final String TAG = "auto_playback";

    /**
     * 自动播放的item
     */
    public static final String AUTO_PLAY_ITEM = "auto_play";

    /**
     * 曝光item
     */
    public static final String EXPOSURE_ITEM = "exposure";

    /**
     * 初始化自动播放检测机制，实际是添加一个空的Fragment
     *
     * @param context
     * @return
     */
    public static AutoPlaybackFragment init(Context context) {
        AutoPlaybackFragment fragment = find(context);
        if (fragment == null) {
            final Activity activity = reviseActivity(context);
            if (activity != null) {
                fragment = AutoPlaybackFragment.create();
                try {
                    FragmentManager manager = activity.getFragmentManager();
                    manager.beginTransaction().add(fragment, TAG).commit();
                    manager.executePendingTransactions();
                } catch (Exception e) {
                }
            }
        }
        return fragment;
    }

    /**
     * 在一个ViewGroup上绑定自动检测机制
     *
     * @param viewGroup
     * @return
     */
    public static Builder bind(ViewGroup viewGroup) {
        return new Builder(viewGroup);
    }

    /**
     * 手动检测，并激活item
     *
     * @param targetView
     */
    public static void activate(final ViewGroup targetView) {
        // 手动检测主要用于多选项卡页面（ViewPager + Fragment），如feed，cardlist，profile等，
        // 每个Fragment中都有一个ListView绑定自动检测机制，
        // 在切换不同的选项卡时，需要手动激活对应页面的自动检测。
        // 如果Activity中只有一个流，无须手动，自动播放机制就可以解决绝大部分问题
        if (isTargetValid(targetView)) {
            // delay的原因：
            // 在使用ViewPager + Fragment的页面中，手动点击tab切换ViewPager，
            // 无论在Fragment.userVisibleHint、Fragment.onResume、ViewPager.onPageSelected、ViewTreeObserver.GlobalLayout、ViewTreeObserver.onFocusChanged等等回调中，
            // 通过View.getLocationInWindow、View.getGlobalVisibleRect等方法，计算出来的View的可见区域均为错误的结果，导致自动检测失败。
            // 因此延迟处理，延迟时间为经验值，不能保证延迟后计算的结果仍然正确。。
            // 另外，滑动ViewPager进行切换并不会导致计算错误，所以延迟会牺牲这部分交互
            final AutoPlaybackFragment instance = find(targetView.getContext());
            if (instance != null) {
                instance.activateDelay(targetView, 300);
            }
        }
    }

    public static void activateNow(ViewGroup targetView) {
        if (isTargetValid(targetView)) {
            final AutoPlaybackFragment instance = find(targetView.getContext());
            if (instance != null) {
                instance.activate(targetView);
            }
        }
    }

    private static boolean isTargetValid(ViewGroup targetView) {
        if (targetView == null || targetView.getChildCount() == 0) {
            return false;
        }

        final Context context = targetView.getContext();
        if (context == null) {
            return false;
        }
        return true;
    }

    /**
     * 手动停止检测
     * <p>大多数情况下并不需要手动停止，检测机制能够根据页面的声明周期自动切换。适用于有特殊规则下的激活或反激活</p>
     *
     * @param targetView
     */
    public static void deactivate(ViewGroup targetView) {
        if (targetView != null) {
            final AutoPlaybackFragment instance = find(targetView.getContext());
            if (instance != null) {
                instance.deactivate(targetView);
            }
        }
    }

    private static AutoPlaybackFragment find(Context context) {
        final Activity activity = reviseActivity(context);
        if (activity != null) {
            FragmentManager manager = activity.getFragmentManager();
            final AutoPlaybackFragment fragment = (AutoPlaybackFragment) manager.findFragmentByTag(TAG);
            return fragment;
        }
        return null;
    }

    /**
     * 解析需要添加Fragment的Activity，兼容首页ActivityGroup
     *
     * @param context
     * @return
     */
    private static Activity reviseActivity(Context context) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("auto play should run in Activity");
        }

        // 之前为兼容首页ActivityGroup，实际是MainTabActivity中的所有Activity公用一个Fragment
        // 会导致切换回tab页时，会触发所有Detector的检查，做了很多无用功
        // 修改为每个Activity管理独立的自动播放Fragment
        // final Activity activity = (Activity) context;
        // final Activity parentActivity = activity.getParent();
        // return parentActivity != null ? parentActivity : activity;
        return (Activity) context;
    }

    public static class Builder {
        private ViewGroup targetView;
        private int topOffset = 0;
        private int bottomOffset = 0;
        private boolean exposure = true;
        //... 可根据需要扩展

        /*default*/Builder(ViewGroup targetView) {
            this.targetView = targetView;
        }

        /**
         * 默认的激活、反激活是计算每个ItemView的可见区域占比。但可以设置top偏移量，计算可见性时会去掉这部分高度。
         * 比如：视频流使用透明标题栏，虽然ItemView在标题栏下面时仍然是可见的，但计算时需要去掉这部分高度；
         * 或者profile，cardlist等，页面头部有特殊布局，计算时需要去掉遮挡的这部分高度
         *
         * @param offset
         * @return
         */
        public Builder topOffset(int offset) {
            this.topOffset = offset;
            return this;
        }

        /**
         * 同{@link #topOffset(int)}，注意该值为TargetView的高度减去底部被遮挡的高度，如tab条：ListView.height - TabBar.height
         *
         * @param offset
         * @return
         */
        public Builder bottomOffset(int offset) {
            this.bottomOffset = offset;
            return this;
        }

        /**
         * 设置是否需要检测Item曝光
         *
         * @param enable
         * @return
         */
        public Builder exposure(boolean enable) {
            this.exposure = enable;
            return this;
        }

        /**
         * 检测对应的Item
         */
        public void detect() {
            if (targetView == null) {
                return;
            }

            final AutoPlaybackFragment fragment = find(targetView.getContext());
            if (fragment == null) {
                return;
            }

            fragment.bind(targetView, topOffset, bottomOffset, exposure);
        }
    }

}

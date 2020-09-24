package com.demo.autoplay;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * 针对{@link ListView}进行适配
 */
public class ListViewParser implements IViewParser<ListView> {

    private ViewVisibilityDetector mDetector;
    private ListView mTargetView;
    private ScrollDetectListener mListenerProxy;

    public ListViewParser(@NonNull ListView view) {
        this.mTargetView = view;
    }

    @Override
    public void attach(ViewVisibilityDetector detector) {
        mDetector = detector;
        if (mListenerProxy == null) {
            // 对ListView设置滑动监听后会触发一个onScroll检测
            if (mTargetView instanceof ScrollDispatched) {
                mListenerProxy = new ScrollDetectListener(null);
                ((ScrollDispatched) mTargetView).addOnScrollListener(mListenerProxy);
            } else {
                final AbsListView.OnScrollListener listener = hookListViewScrollListener(mTargetView);
                if (listener != null) {
                    mListenerProxy = new ScrollDetectListener(listener);
                    mTargetView.setOnScrollListener(mListenerProxy);
                }
            }
        } else {
            mDetector.detectItem(mTargetView);
        }
    }

    /**
     * 注意：由于代理ListView的OnScrollListener，因此业务方需要在启动自动探测之前设置OnScrollListener，
     * 否则自动探测会失效
     *
     * @param view
     * @return
     */
    private AbsListView.OnScrollListener hookListViewScrollListener(ListView view) {
        if (view == null) {
            return null;
        }

        try {
            Field field = AbsListView.class.getDeclaredField("mOnScrollListener");
            field.setAccessible(true);
            return (AbsListView.OnScrollListener) field.get(view);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void detach() {
        if (mListenerProxy != null) {
            final ListView view = mTargetView;
            if (view instanceof ScrollDispatched) {
                ((ScrollDispatched) view).removeOnScrollListener(mListenerProxy);
            } else {
                // 恢复原始的滑动监听
                final AbsListView.OnScrollListener originalListener = mListenerProxy.object;
                if (view != null) {
                    view.setOnScrollListener(originalListener);
                }
            }
            mListenerProxy = null;
        }
    }

    @Override
    public int getChildCount() {
        return mTargetView.getChildCount();
    }

    @Override
    public int indexOfChild(View view) {
        return mTargetView.indexOfChild(view);
    }

    @Override
    public View getChildAt(int index) {
        return mTargetView.getChildAt(index);
    }

    @Override
    public int getFirstVisiblePosition() {
        return mTargetView.getFirstVisiblePosition();
    }

    private class ScrollDetectListener implements AbsListView.OnScrollListener {

        private AbsListView.OnScrollListener object;

        public ScrollDetectListener(AbsListView.OnScrollListener object) {
            this.object = object;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (object != null) {
                object.onScrollStateChanged(view, scrollState);
            }

            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                VideoPreLoadManager.getInsance().preDownloadList((ListView) view);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (object != null) {
                object.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            // 滑动检测
            mDetector.detectItem(view);
        }
    }
}

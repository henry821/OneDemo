package com.demo.autoplay;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 针对{@link RecyclerView}适配
 */
public class RecyclerViewParser implements IViewParser<RecyclerView> {

    private ViewVisibilityDetector mDetector;
    private RecyclerView mTargetView;
    private RecyclerView.OnScrollListener mDetectListener;

    public RecyclerViewParser(@NonNull RecyclerView recyclerView) {
        mTargetView = recyclerView;
    }

    @Override
    public void attach(ViewVisibilityDetector detector) {
        mDetector = detector;
        if (mDetectListener == null) {
            mDetectListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    mDetector.detectItem(recyclerView);
                }
            };
        }
        mTargetView.removeOnScrollListener(mDetectListener);
        mTargetView.addOnScrollListener(mDetectListener);
        mDetector.detectItem(mTargetView); // 手动进行一次检测
    }

    @Override
    public void detach() {
        mTargetView.removeOnScrollListener(mDetectListener);
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
        return getLayoutManager(mTargetView).getChildAt(index);
    }

    @Override
    public int getFirstVisiblePosition() {
        return getLayoutManager(mTargetView).findFirstVisibleItemPosition();
    }

    private LinearLayoutManager getLayoutManager(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (!(manager instanceof LinearLayoutManager)) {
            throw new UnsupportedOperationException("Support LinearLayoutManager only, but current is "
                    + (manager != null ? manager.getClass().getSimpleName() : null));
        }
        return (LinearLayoutManager) manager;
    }

}

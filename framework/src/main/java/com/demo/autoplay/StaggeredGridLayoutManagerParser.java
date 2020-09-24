package com.demo.autoplay;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 目前只支持曝光，不支持自动播放
 */
public class StaggeredGridLayoutManagerParser implements IViewParser<RecyclerView> {

    private ViewVisibilityDetector mDetector;
    private RecyclerView mTargetView;
    private RecyclerView.OnScrollListener mDetectListener;

    public StaggeredGridLayoutManagerParser(@NonNull RecyclerView recyclerView) {
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

    //todo:不支持自动播放，如果需要支持，后续需要改造该方法
    @Override
    public int getFirstVisiblePosition() {
        return 0;
    }

    private StaggeredGridLayoutManager getLayoutManager(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        return (StaggeredGridLayoutManager) manager;
    }

}

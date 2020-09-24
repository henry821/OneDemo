package com.demo.autoplay;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * 简单的视图解析，将所有视图都视为在屏幕上显示
 * Created by chenggan on 2019/5/7.
 */
public class SimpleViewParser implements IViewParser<ViewGroup> {

    private ViewGroup mTargetView;
    private ViewVisibilityDetector mDetector;

    public SimpleViewParser(@NonNull ViewGroup viewGroup) {
        this.mTargetView = viewGroup;
    }

    @Override
    public void attach(ViewVisibilityDetector detector) {
        this.mDetector = detector;
        mDetector.detectItem(mTargetView);
    }

    @Override
    public void detach() {

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
        return 0;
    }
}

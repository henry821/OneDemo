package com.demo.autoplay;

import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


/**
 * 针对{@link ListView}进行适配
 */
public class ViewPagerParser implements IViewParser<ViewPager> {

    private ViewVisibilityDetector mDetector;
    private ViewPager mTargetView;
    private boolean hasListener;

    public ViewPagerParser(@NonNull ViewPager view) {
        this.mTargetView = view;
    }


    @Override
    public void attach(ViewVisibilityDetector detector) {
        mDetector = detector;
        if (!hasListener) {
            hasListener = true;
            mTargetView.addOnPageChangeListener(mOnPageChangeListener);
            mDetector.detectItem(mTargetView);
        } else {
            mDetector.detectItem(mTargetView);
        }
    }

    @Override
    public void detach() {
        hasListener = false;
        mTargetView.removeOnPageChangeListener(mOnPageChangeListener);
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            mDetector.detectItem(mTargetView);
        }

        @Override
        public void onPageSelected(int i) {
            mDetector.detectItem(mTargetView);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

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

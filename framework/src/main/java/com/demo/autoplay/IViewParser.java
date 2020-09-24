package com.demo.autoplay;

import android.view.View;
import android.view.ViewGroup;

/**
 * 针对不同的ViewGroup解析对应的item
 * Created by kaige1 on 2018/8/1.
 */
public interface IViewParser<T extends ViewGroup> {

    void attach(ViewVisibilityDetector detector);

    void detach();

    int getChildCount();

    int indexOfChild(View view);

    View getChildAt(int index);

    int getFirstVisiblePosition();
}

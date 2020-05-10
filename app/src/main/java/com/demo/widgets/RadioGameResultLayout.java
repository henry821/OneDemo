package com.demo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description 电台房游戏结果展示布局
 * Author wanghengwei
 * Date   2020/5/9 12:00
 */
public class RadioGameResultLayout extends ViewGroup {
    private static final int MAX_CHILDREN = 3;
    /**
     * 布局宽度
     */
    private int width;
    /**
     * 布局高度
     */
    private int height;

    public RadioGameResultLayout(@NonNull Context context) {
        this(context, null);
    }

    public RadioGameResultLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioGameResultLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();

        //最多只支持3个子布局
        for (int i = 0; i < (Math.min(childCount, MAX_CHILDREN)); i++) {
            //获取子View
            View child = getChildAt(i);
            //计算子View宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //拿到子View的宽高
            width += child.getMeasuredWidth();
            height = Math.max(height, child.getMeasuredHeight());
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int usedWidth = 0;
        for (int i = 0; i < Math.min(childCount, MAX_CHILDREN); i++) {
            View child = getChildAt(i);
            child.layout(usedWidth, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            usedWidth += child.getMeasuredWidth();
        }
    }

}

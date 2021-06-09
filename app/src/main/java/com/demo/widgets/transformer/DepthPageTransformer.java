package com.demo.widgets.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer{

    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.85f;

    private static final float MAX_ALPHA = 1.0f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        ViewPager viewPager;
        if (page.getParent() instanceof ViewPager) {
            viewPager = ((ViewPager) page.getParent());
        } else {
            return;
        }
        position = getPositionConsiderPadding(viewPager, page);
        if (position <= 1) {
            //   1.2f + (1-1)*(1.2-1.0)
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            float alphaFactor = MIN_ALPHA + (1 - Math.abs(position)) * (MAX_ALPHA - MIN_ALPHA);
            page.setScaleX(scaleFactor);  //缩放效果
            page.setAlpha(alphaFactor);
            if (position > 0) {
                page.setTranslationX(-scaleFactor * 2);
            } else if (position < 0) {
                page.setTranslationX(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }

    private float getPositionConsiderPadding(ViewPager viewPager, View page) {
        // padding影响了position，自己生成position
        int clientWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
        return (float) (page.getLeft() - viewPager.getScrollX() - viewPager.getPaddingLeft()) / clientWidth;
    }
}

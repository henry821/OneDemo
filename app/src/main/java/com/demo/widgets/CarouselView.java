package com.demo.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.one.R;
import com.demo.utils.ScreenUtil;
import com.demo.base.transformer.DepthPageTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图控件，ViewPager实现
 * Created by hengwei on 2021/6/4.
 */
public class CarouselView extends ViewPager {

    private boolean autoScroll;

    public CarouselView(@NonNull Context context) {
        super(context);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        ThumbnailAdapter adapter = new ThumbnailAdapter();
        setAdapter(adapter);
        setPageTransformer(true, new DepthPageTransformer());
        setPageMargin(ScreenUtil.INSTANCE.convertDpToPx(context, 65));

        ViewPagerScroller scroller = new ViewPagerScroller(context, new OvershootInterpolator());
        scroller.initViewPagerScroll(this);

        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                if (!autoScroll) {
                    return;
                }
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setCurrentItem(position + 1, true);
                    }
                }, 1000);
            }
        });
    }

    public void startScroll() {
        autoScroll = true;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(1, true);
            }
        }, 1000);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return autoScroll || super.dispatchTouchEvent(ev);
    }

    /**
     * ViewPager 滚动速度设置
     */
    private static class ViewPagerScroller extends Scroller {

        private static final int SMOOTH_SCROLL_DURATION = 600;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, SMOOTH_SCROLL_DURATION);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, SMOOTH_SCROLL_DURATION);
        }


        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ThumbnailAdapter extends PagerAdapter {

        private final List<Integer> dataList;
        private final List<ThumbnailView> availableViewPool;

        public ThumbnailAdapter() {
            availableViewPool = new ArrayList<>();
            dataList = new ArrayList<>();
            dataList.add(R.drawable.scene1);
            dataList.add(R.drawable.scene2);
            dataList.add(R.drawable.scene3);
            dataList.add(R.drawable.scene4);
            dataList.add(R.drawable.scene5);
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPosition = position % dataList.size();
            ThumbnailView thumbnailView = pickAvailableView(container.getContext());
            thumbnailView.loadImage(dataList.get(realPosition));
            thumbnailView.setPage(realPosition + 1, dataList.size());

            container.addView(thumbnailView);

            return thumbnailView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            availableViewPool.add((ThumbnailView) object);
        }

        /**
         * 获取可用的View
         *
         * @param context 上下文
         * @return 可用的View
         */
        private ThumbnailView pickAvailableView(Context context) {
            ThumbnailView thumbnailView;
            if (availableViewPool.isEmpty()) {
                thumbnailView = new ThumbnailView(context);
            } else {
                thumbnailView = availableViewPool.remove(0);
            }
            return thumbnailView;
        }

    }

    private static class ThumbnailView extends CardView {

        private final ImageView ivThumbnail;
        private final TextView tvPage;

        public ThumbnailView(@NonNull Context context) {
            super(context);
        }

        public ThumbnailView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public ThumbnailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        {
            inflate(getContext(), R.layout.layout_thumbnail_view, this);
            ivThumbnail = findViewById(R.id.iv_thumbnail);
            tvPage = findViewById(R.id.tv_page);

            setCardBackgroundColor(Color.WHITE);
            setRadius(ScreenUtil.INSTANCE.convertDpToPx(getContext(), 7));
            setContentPadding(ScreenUtil.INSTANCE.convertDpToPx(getContext(), 5),
                    ScreenUtil.INSTANCE.convertDpToPx(getContext(), 5),
                    ScreenUtil.INSTANCE.convertDpToPx(getContext(), 5),
                    ScreenUtil.INSTANCE.convertDpToPx(getContext(), 2));

        }

        public void loadImage(int resId) {
            ivThumbnail.setImageResource(resId);
        }

        public void setPage(int currentPage, int totalPage) {
            tvPage.setText(String.format("%s/%s", currentPage, totalPage));
        }
    }

}

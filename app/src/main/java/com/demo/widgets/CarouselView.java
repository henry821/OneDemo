package com.demo.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.one.R;
import com.demo.utils.ScreenUtil;
import com.demo.widgets.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图控件，ViewPager实现
 * Created by hengwei on 2021/6/4.
 */
public class CarouselView extends ViewPager {

    private static final float ANIMATION_COUNT = 100;

    //步长
    private float step;
    //当前进度对应的滑动距离
    private float offset;
    //上一进度对应的滑动距离
    private float lastOffset;

    private ThumbnailAdapter adapter;
    private ValueAnimator animator;

    public CarouselView(@NonNull Context context) {
        super(context);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        adapter = new ThumbnailAdapter();
        setAdapter(adapter);
        setPageTransformer(true, new DepthPageTransformer());
//        setPageMargin(100);

        animator = ValueAnimator.ofFloat(0, ANIMATION_COUNT);
        animator.setDuration(300);
//        animator.setInterpolator(new BounceInterpolator());
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setStartDelay(1000);
    }

    /**
     * 开启滚动
     */
    public void startScroll() {
        //需要的对象没有初始化则直接返回
        if (animator == null || adapter == null) {
            return;
        }
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                step = getMeasuredWidth() / ANIMATION_COUNT;
                beginFakeDrag();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFakeDragging()) {
                    endFakeDrag();
                    postAnimatorEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (isFakeDragging()) {
                    endFakeDrag();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                offset = value * step + getMeasuredWidth() * getCurrentItem();
                //计算出当前需要拖动距离
                float dragBy = offset - lastOffset;
                //拖动ViewPager
                if (isFakeDragging()) {
                    fakeDragBy(-dragBy);
                }
                //保存已滑动距离
                lastOffset = offset;
            }
        });
        animator.start();
    }

    /**
     * 取消动画
     */
    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

    private void postAnimatorEnd() {
        if (getCurrentItem() < adapter.getCount() - 1) {
            animator.start();
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

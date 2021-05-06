package com.demo.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.demo.one.R;
import com.demo.utils.ScreenUtil;

/**
 * 简易TabLayout
 */
public class SimpleTabLayout extends HorizontalScrollView implements ValueAnimator.AnimatorUpdateListener {

    private ViewPager pager; //需要绑定的ViewPager
    private final PageListener pageListener = new PageListener();

    private final Handler mHandler;

    private final LinearLayout tabsContainer;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private final boolean shouldExpand;
//    private final boolean textAllCaps;

    private final int tabPadding;

    private final int tabBackgroundResId;

    private final boolean useActiveColor = false;

    private int newStyleInActiveTabTextColor;
    private int newStyleActiveTabTextColor;
    private final RectF mIndicatorLineRect = new RectF();
    private int indicatorLineHeight = 0;
    private Paint indicatorLinePaint;
    private int indicatorLineWidth;
    private LinearGradient mLinearGradientView;
    private int[] gradientColors;
    private final int[] indicatorColors = new int[]{Color.parseColor("#FE9600"), Color.parseColor("#FE7E00"), Color.parseColor("#FF6800"), Color.parseColor("#FF5100"), Color.parseColor("#FF3900")};
    private boolean mIsClickEvent = false;
    private final Rect mStartViewRect = new Rect();
    private final Rect mEndViewRect = new Rect();
    private final Rect mMyRect = new Rect();
    private ObjectAnimator mObjectAnimator;
    private ValueAnimator mValueAnimator;
    private final AnimatorListenerAdapter mAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            mIsClickEvent = false;
        }
    };

    private final int dp_3;
    private final int dp_24;

    private final int startEndPadding;

    public SimpleTabLayout(Context context) {
        this(context, null);
    }

    public SimpleTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new Handler();
        dp_3 = ScreenUtil.INSTANCE.convertDpToPx(context, 3);
        dp_24 = ScreenUtil.INSTANCE.convertDpToPx(context, 24);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        tabsContainer.setLayoutParams(params);
        addView(tabsContainer);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleTabLayout);

        tabPadding = a.getDimensionPixelSize(R.styleable.SimpleTabLayout_tabPaddingLeftRight,
                ScreenUtil.INSTANCE.convertDpToPx(context, 24));
        tabBackgroundResId = a.getResourceId(R.styleable.SimpleTabLayout_tabBackground, R.drawable.page_background_tab);
        shouldExpand = a.getBoolean(R.styleable.SimpleTabLayout_shouldExpand, false);
//        textAllCaps = a.getBoolean(R.styleable.SimpleTabLayout_textAllCaps, false);

        a.recycle();

        initNewStyleIndicator();

        startEndPadding = getResources().getDimensionPixelOffset(R.dimen.pagesliging_tablayout_tab_padding);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        final int currentLineMiddleX = (int) animation.getAnimatedValue();
        mIndicatorLineRect.left = currentLineMiddleX - indicatorLineWidth / 2.0f;
        mIndicatorLineRect.right = currentLineMiddleX + indicatorLineWidth / 2.0f;
        mIndicatorLineRect.bottom = getHeight() - dp_3;
        mIndicatorLineRect.top = mIndicatorLineRect.bottom - indicatorLineHeight;

        //计算下划线颜色
        if (tabsContainer.getChildCount() > 0) {
            View firstChildView = tabsContainer.getChildAt(0);
            View lastChildView = tabsContainer.getChildAt(tabsContainer.getChildCount() - 1);
            if (firstChildView == lastChildView) {
                return;
            }
            mLinearGradientView = new LinearGradient(firstChildView.getLeft() + firstChildView.getPaddingLeft(), 0,
                    lastChildView.getRight() - lastChildView.getPaddingRight(), 0,
                    gradientColors, null, Shader.TileMode.CLAMP);

            indicatorLinePaint.setShader(mLinearGradientView);
        } else {//将下划线变为纯色
            indicatorLinePaint.setShader(null);
            indicatorLinePaint.setColor(gradientColors[currentPosition]);
        }
        invalidate();
    }

    /**
     * 绑定ViewPager
     *
     * @param pager ViewPager
     */
    public void bindViewPager(ViewPager pager) {
        if (pager == null) {
            return;
        }
        this.pager = pager;
        pager.addOnPageChangeListener(pageListener);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     */
    public void notifyDataSetChanged() {

        if (pager.getAdapter() == null) {
            return;
        }

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        viewHolders = new ViewHolder[tabCount];

        for (int i = 0; i < tabCount; i++) {
            addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition);
            }
        });

        updateNewIndicatorStyle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tabsContainer.getChildCount() == 0) {
            return;
        }
        indicatorLinePaint.setColor(Color.WHITE);
        if (!mIsClickEvent) {
            computeIndicatorLineRect();
        }
        canvas.drawRoundRect(mIndicatorLineRect, indicatorLineHeight / 2.0f, indicatorLineHeight / 2.0f, indicatorLinePaint);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
        }
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrollToChild(currentPosition);
    }

    private void initNewStyleIndicator() {
        indicatorLineWidth = dp_24;
        indicatorLineHeight = dp_3;
        newStyleInActiveTabTextColor = getResources().getColor(R.color.gray_939393);
        newStyleActiveTabTextColor = getResources().getColor(R.color.main_content_text_color);
        indicatorLinePaint = new Paint();
        indicatorLinePaint.setAntiAlias(true);
        indicatorLinePaint.setStyle(Paint.Style.FILL);
    }

    private void updateNewIndicatorStyle() {
        gradientColors = new int[tabsContainer.getChildCount()];
        for (int index = 0; index < tabsContainer.getChildCount(); index++) {
            int color;
            if (index > indicatorColors.length - 1) {
                color = indicatorColors[indicatorColors.length - 1];
            } else {
                color = indicatorColors[index];
            }
            gradientColors[index] = color;
        }
    }

    private ViewHolder[] viewHolders;

    private void addTextTab(final int position, String title) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);

        if (viewHolders != null && position < viewHolders.length) {
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.itemView = textView;
            viewHolders[position] = viewHolder;
        }
        addTab(position, textView);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startIndicatorAnim(v);
                setPagerCurrentItem(pager, position);
            }
        });

        LinearLayout.LayoutParams layoutParams;
        if (shouldExpand) {
            layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        } else {
            layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            int bottomPadding = 0;
            int topPadding = 0;
            tab.setPadding(0, topPadding, 0, bottomPadding);//扩大点击区域
        }

        updateTabLayoutParams(position, tab, layoutParams);
        tabsContainer.addView(tab, position);
    }

    /**
     * 点击tab时，跳转到ViewPager的特定页面
     *
     * @param position 位置index
     */
    private void setPagerCurrentItem(ViewPager pager, int position) {
        // 设置方法在view pager onLayout一次会触发动画，与iOS不统一，且会造成一些多余的操作
        pager.setCurrentItem(position);
    }

    private void updateTabLayoutParams(int position, View tab, LinearLayout.LayoutParams layoutParams) {

        if (position >= 0 && position < tabCount) {
            LeftRightMargin leftRightMargin = getItemMarginByIndex(position, tabCount);
            layoutParams.leftMargin = leftRightMargin.leftMargin;
            layoutParams.rightMargin = leftRightMargin.rightMargin;
            tab.setLayoutParams(layoutParams);
        }
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            ViewHolder viewHolder = viewHolders != null && i < viewHolders.length ? viewHolders[i] : null;
            viewHolder.itemView.setBackgroundResource(tabBackgroundResId);

            if (i < tabsContainer.getChildCount() && tabsContainer.getChildAt(i) != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabsContainer.getChildAt(i).getLayoutParams();
                updateTabLayoutParams(i, tabsContainer.getChildAt(i), layoutParams);
            }
            //根据使用的模式 去控制时否显示subtile
            TextView tab = (TextView) viewHolder.itemView;
            if (tab != null) {
                if (useActiveColor && pager.getCurrentItem() == i) {
                    tab.setTextColor(newStyleActiveTabTextColor);
                    tab.getPaint().setFakeBoldText(true);
                    viewHolder.setSelect(true);
                } else {
                    tab.setTextColor(newStyleInActiveTabTextColor);
                    tab.getPaint().setFakeBoldText(false);
                    viewHolder.setSelect(false);
                }

                // setAllCaps() is only available from API 14, so the upper case
                // is made manually if we are on a
                // pre-ICS-build
//                if (textAllCaps) {
//                    tab.setAllCaps(true);
//                }
            }
        }

    }

    private void scrollToChild(final int position) {

        mHandler.removeCallbacksAndMessages(null);
        long delay = mIsClickEvent ? (mValueAnimator.getDuration() - mValueAnimator.getCurrentPlayTime()) : 0L;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (tabCount == 0 || tabsContainer == null) {
                    return;
                }
                if (position < tabsContainer.getChildCount()) {
                    if (mObjectAnimator != null && mObjectAnimator.isRunning()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            mObjectAnimator.pause();
                        }
                        mObjectAnimator.cancel();
                    }
                    View checkView = tabsContainer.getChildAt(position);
                    int k = checkView.getMeasuredWidth();
                    int l = checkView.getLeft();
                    int i2 = +l + k / 2 - getWidth() / 2;
                    // 动画实现位移
                    mObjectAnimator = ObjectAnimator.ofInt(SimpleTabLayout.this, "scrollX", i2);
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();
                }
            }
        }, delay);
    }

    //平移动画
    private void startIndicatorAnim(View nextView) {
        if (pager == null || pager.getCurrentItem() >= tabsContainer.getChildCount() || tabsContainer.getChildCount() == 0) {
            mIsClickEvent = false;
            return;
        }

        View mEnSubView = null;

        //动画开始View
        View mStartView = tabsContainer.getChildAt(pager.getCurrentItem());
        View mStartsubView = null;
        //动画终止View
        if (mStartView == nextView) {
            mIsClickEvent = false;
            return;
        }
        mIsClickEvent = true;

        mStartView.getGlobalVisibleRect(mStartViewRect);
        nextView.getGlobalVisibleRect(mEndViewRect);
        int mStartViewX = mStartViewRect.centerX();
        int mEndViewX = mEndViewRect.centerX();
        if (mStartViewX < 0 || mStartViewX > ScreenUtil.INSTANCE.getScreenWidth(getContext())) {
            getGlobalVisibleRect(mMyRect);
            mStartViewX += mMyRect.left;
        }
        long mAnimDuration = 300;
        if (mEnSubView != null && mStartsubView != null && mStartsubView.getVisibility() == VISIBLE) {
            mValueAnimator = ValueAnimator.ofInt(mStartView.getLeft() + mStartsubView.getLeft() + mStartsubView.getMeasuredWidth() / 2, mStartView.getLeft() + mStartsubView.getLeft() + mStartsubView.getMeasuredWidth() / 2 + mEndViewX - mStartViewX).setDuration(mAnimDuration);
        } else {
            mValueAnimator = ValueAnimator.ofInt(mStartView.getLeft() + mStartView.getMeasuredWidth() / 2, mStartView.getLeft() + mStartView.getMeasuredWidth() / 2 + mEndViewX - mStartViewX).setDuration(mAnimDuration);
        }
        mValueAnimator.addListener(mAnimatorListenerAdapter);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.start();

    }

    private void computeIndicatorLineRect() {

        float indicatorLineStartX; //指示线滑动范围的最左端的X轴坐标
        float indicatorLineEndX; //指示线滑动范围的最右端的X轴坐标
        float indicatorLineShowStartX; //指示器显示范围的左侧X轴坐标
        float indicatorLineShowEndX; //指示器显示范围的右侧X轴坐标

        View selectedView;
        View selectedSubView = null;
        View pendingView;
        View pendingSubView = null;
        View selectedTextView = null;
        View pendingTextView = null;

        selectedView = tabsContainer.getChildAt(currentPosition);

        if (selectedView != null) {
            if (selectedView.getTag() != null) {
                ViewHolder viewHolder = (ViewHolder) selectedView.getTag();
                selectedTextView = viewHolder.itemView;
            }
        }

        if (currentPosition == tabsContainer.getChildCount() - 1) {
            pendingView = tabsContainer.getChildAt(currentPosition);
        } else {
            pendingView = tabsContainer.getChildAt(currentPosition + 1);
        }

        if (pendingView != null) {
            if (pendingView.getTag() != null) {
                ViewHolder viewHolder = (ViewHolder) pendingView.getTag();
                pendingTextView = viewHolder.itemView;
            }
        }

        boolean flagSelect = selectedSubView != null && selectedSubView.getVisibility() == VISIBLE;
        boolean flagPending = pendingSubView != null && pendingSubView.getVisibility() == VISIBLE;

        int selected_width = flagSelect ? selectedSubView.getMeasuredWidth() : selectedView.getMeasuredWidth();
        int pendding_width = flagPending ? pendingSubView.getMeasuredWidth() : pendingView.getMeasuredWidth();

        Pair<Integer, Integer> mIndicatorRangerPair;
        Pair<Integer, Integer> mNextTabIndicatorRange;

        //是否以文字为中心画下划线
        boolean mDrawUnderlineCenterText = false;
        if (flagSelect) {
            mIndicatorRangerPair = new Pair<>(tabsContainer.getLeft() + selectedView.getLeft() + selectedSubView.getLeft() + (selected_width - indicatorLineWidth) / 2, indicatorLineWidth);
        } else {
            if (mDrawUnderlineCenterText) {
                mIndicatorRangerPair = new Pair<>(selectedView.getLeft() + selectedTextView.getLeft() + (selectedTextView.getMeasuredWidth() - indicatorLineWidth) / 2, indicatorLineWidth);
            } else {
                mIndicatorRangerPair = new Pair<>(tabsContainer.getLeft() + selectedView.getLeft() + (selected_width - indicatorLineWidth) / 2, indicatorLineWidth);
            }
        }

        if (flagPending) {
            mNextTabIndicatorRange = new Pair<>(tabsContainer.getLeft() + pendingView.getLeft() + pendingSubView.getLeft() + (pendding_width - indicatorLineWidth) / 2, indicatorLineWidth);
        } else {
            if (mDrawUnderlineCenterText) {
                mNextTabIndicatorRange = new Pair<>(pendingView.getLeft() + pendingTextView.getLeft() + (pendingTextView.getMeasuredWidth() - indicatorLineWidth) / 2, indicatorLineWidth);
            } else {
                mNextTabIndicatorRange = new Pair<>(tabsContainer.getLeft() + pendingView.getLeft() + (pendding_width - indicatorLineWidth) / 2, indicatorLineWidth);

            }
        }

        indicatorLineStartX = mIndicatorRangerPair.first; //指示线滑动范围的最左端X轴坐标
        indicatorLineEndX = mNextTabIndicatorRange.first + mNextTabIndicatorRange.second;

        float calculateOffsetRatio;

        if (0 <= currentPositionOffset && currentPositionOffset < 0.5) {
            calculateOffsetRatio = currentPositionOffset * 2;
            if (mIsClickEvent) {
                calculateOffsetRatio = 0;
            }
            indicatorLineShowStartX = indicatorLineStartX;
            indicatorLineShowEndX = indicatorLineStartX + mIndicatorRangerPair.second + (indicatorLineEndX - indicatorLineShowStartX - mIndicatorRangerPair.second) * calculateOffsetRatio;
        } else {
            calculateOffsetRatio = (currentPositionOffset - 0.5f) * 2.0f;
            if (calculateOffsetRatio > 1.0f) {
                calculateOffsetRatio = 1.0f;
            }
            if (mIsClickEvent) {
                calculateOffsetRatio = 1;
            }
            indicatorLineShowStartX = indicatorLineStartX + (indicatorLineEndX - indicatorLineStartX - mNextTabIndicatorRange.second) * calculateOffsetRatio;
            indicatorLineShowEndX = indicatorLineEndX;
        }
        mIndicatorLineRect.left = indicatorLineShowStartX;
        mIndicatorLineRect.right = indicatorLineShowEndX;
        if (pendingSubView != null) {
            mIndicatorLineRect.bottom = pendingSubView.getBottom();
        } else {
            mIndicatorLineRect.bottom = getHeight() - dp_3;
        }
        mIndicatorLineRect.top = mIndicatorLineRect.bottom - indicatorLineHeight;

        if (tabsContainer.getChildCount() <= 1) {
            return;
        }
        View firstChildView = tabsContainer.getChildAt(0);
        View lastChildView = tabsContainer.getChildAt(tabsContainer.getChildCount() - 1);

        mLinearGradientView = new LinearGradient(firstChildView.getLeft() + firstChildView.getPaddingLeft(), 0,
                lastChildView.getRight() - lastChildView.getPaddingRight(), 0,
                gradientColors, null, Shader.TileMode.CLAMP);

        indicatorLinePaint.setShader(mLinearGradientView);
    }

    private class PageListener implements ViewPager.OnPageChangeListener {
        public int prevPage = 0;
        public int curPage = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;

            View child = tabsContainer.getChildAt(position);
            if (child == null) {
                return;
            }

            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageSelected(int position) {
            prevPage = curPage;
            curPage = position;
            updateTabStyles();
            if (useActiveColor) {
                if (curPage < tabsContainer.getChildCount() && prevPage < tabsContainer.getChildCount()) {
                    ViewHolder viewHolder = viewHolders != null && prevPage < viewHolders.length ? viewHolders[prevPage] : null;
                    if (viewHolder != null) {
                        TextView tab = (TextView) viewHolder.itemView;
                        tab.setTextColor(newStyleInActiveTabTextColor);
                        tab.getPaint().setFakeBoldText(false);
                        tab.invalidate();
                        viewHolder.setSelect(false);
                        // modify previous tab to display active text
                        viewHolder = viewHolders != null && curPage < viewHolders.length ? viewHolders[curPage] : null;
                        if (viewHolder != null) {
                            viewHolder.setSelect(true);
                            tab = (TextView) viewHolder.itemView;
                            if (tab != null) {
                                tab.setTextColor(newStyleActiveTabTextColor);
                                tab.getPaint().setFakeBoldText(true);
                                tab.invalidate();
                            }
                        }
                    }
                }
            }
            scrollToChild(position);

        }
    }

    /**
     * 根据 page position 获取对应 item 的 margin 值
     *
     * @param position page position
     * @return LeftRightMargin
     */
    private LeftRightMargin getItemMarginByIndex(int position, int tabCount) {
        LeftRightMargin margin = new LeftRightMargin();
        if (position == tabCount - 1) {
            margin.rightMargin = startEndPadding * 2;
        } else if (position == 0) {
            margin.leftMargin = startEndPadding * 2;
            margin.rightMargin = tabPadding * 2;
        } else {
            margin.rightMargin = tabPadding * 2;
        }
        return margin;
    }

    /**
     * tab 对应的view 封装
     */
    private static class ViewHolder {
        private View itemView;

        public void setSelect(boolean select) {
            if (itemView != null) {
                itemView.setSelected(select);
            }
        }
    }


    private static class LeftRightMargin {
        public int leftMargin = 0;
        public int rightMargin = 0;
    }

}

package com.demo.autoplay;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.demo.autoplay.ViewVisibilityHelper.calculateVisiblePercents;
import static com.demo.autoplay.ViewVisibilityHelper.calculateVisiblePercentsWithTopOffset;

/**
 * 在一个ViewGroup中按照一定的策略查找特定的ItemView，并对响应的Item进行激活和反激活，
 * 用于视频、360图片等在滑动过程中的自动播放
 *
 * <p>这只是一个基础的实现，与具体的ViewGroup和业务（Item类型）无关，因此可以方便的集成到相似的场景中。</p>
 *
 * <p>支持多种协议的Item，每种item有对应规则和实现，如自动播放、曝光等</p>
 * <p>
 * Created by kaige1 on 14/12/2017.
 */
public class ViewVisibilityDetector {

    public static class DetectParams {

        /**
         * 默认的激活、反激活是计算每个ItemView的可见区域占比。但可以设置top偏移量，计算可见性时会去掉这部分高度。
         * 比如：视频流使用透明标题栏，虽然ItemView在标题栏下面时仍然是可见的，但计算时需要去掉这部分高度；
         * 或者profile，cardlist等，页面头部有特殊布局，计算时需要去掉遮挡的这部分高度
         */
        public int topOffset;

        /**
         * 同{@link #topOffset}，
         * 注意该值为TargetView的高度减去底部被遮挡的高度，如tab条：ListView.height - TabBar.height
         */
        public int bottomOffset;

        /**
         * 当次检测时children的可见度
         */
        public Map<View, Float> childrenVisibility;
    }

    /*package*/ IViewParser viewParser;
    /*package*/ boolean enable = true;
    /*package*/ DetectParams detectParams;
    private Map<String, IDetectorImpl> mImpls = new HashMap<>();

    public ViewVisibilityDetector(IViewParser parser, int topOffset, int bottomOffset) {
        viewParser = parser;
        detectParams = new DetectParams();
        detectParams.topOffset = topOffset;
        detectParams.bottomOffset = bottomOffset;
    }

    /**
     * 开始探测，并激活Item
     */
    public void start() {
        enable = true;
        viewParser.attach(this);
    }

    /**
     * 暂停探测：
     * 不同于{@link #stop()}，暂停后只是不再进行计算，并不改变当前活跃的item，
     * 直到{@link #resume()}开始重新探测计算
     */
    public void pause() {
        enable = false;
    }

    /**
     * 重新开始探测，并激活符合规则的Item
     */
    public void resume() {
        enable = true;
        viewParser.attach(this);
    }

    /**
     * 停止探测，并反激活处于活跃状态的Item
     */
    public void stop() {
        enable = false;
        viewParser.detach();
        if (detectParams != null && detectParams.childrenVisibility != null) {
            detectParams.childrenVisibility.clear();
            detectParams.childrenVisibility = null;
        }

        // 探测结束时，通知具体的探测器做清理工作
        Iterator<Map.Entry<String, IDetectorImpl>> iterator = mImpls.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, IDetectorImpl> entry = iterator.next();
            if (entry != null) {
                IDetectorImpl impl = entry.getValue();
                if (impl != null) {
                    impl.stop();
                }
            }
        }
    }

    /**
     * 添加需要检测的item类型
     *
     * @param item
     */
    public void addTarget(String item) {
        if (!TextUtils.isEmpty(item)) {
            if (mImpls == null) {
                mImpls = new HashMap<>();
            }
            if (!mImpls.containsKey(item)) {
                IDetectorImpl impl = DetectorFactory.makeImpl(this, item);
                mImpls.put(item, impl);
            }
        }
    }

    /**
     * 执行一次检测
     *
     * @param viewGroup
     */
    public void detectItem(ViewGroup viewGroup) {
        final int count = viewParser.getChildCount();
        if (count == 0) {
            return;
        }

        if (mImpls == null || mImpls.isEmpty()) {
            return;
        }

        // 1.计算子view的可见度
        // 这里对当次探测的可见度结果进行了缓存，用于后续的规则，避免重复计算
        if (detectParams.childrenVisibility == null) {
            detectParams.childrenVisibility = new HashMap<>();
        }
        detectParams.childrenVisibility.clear();

        for (int index = 0; index < count; index++) {
            final View child = viewParser.getChildAt(index);
            if (child instanceof DetectableItemView) {
                View detectView = ((DetectableItemView) child).getDetectedView();
                if (detectView != null) {
                    final float percent;
                    if (detectParams.topOffset > 0) {
                        percent = calculateVisiblePercentsWithTopOffset(detectView, detectParams.topOffset);
                    } else {
                        percent = calculateVisiblePercents(detectView);
                    }
                    detectParams.childrenVisibility.put(detectView, percent);
                }
            }
        }

        // 2.调用具体的探测器按各自的规则检测
        Iterator<Map.Entry<String, IDetectorImpl>> iterator = mImpls.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, IDetectorImpl> entry = iterator.next();
            if (entry != null) {
                IDetectorImpl impl = entry.getValue();
                if (impl != null) {
                    impl.detect(viewGroup);
                }
            }
        }
    }
}

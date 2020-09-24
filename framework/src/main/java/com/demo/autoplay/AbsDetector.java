package com.demo.autoplay;

import android.view.View;

import java.util.Map;

import static com.demo.autoplay.ViewVisibilityHelper.calculateVisiblePercents;
import static com.demo.autoplay.ViewVisibilityHelper.calculateVisiblePercentsWithTopOffset;

/**
 * Created by kaige1 on 2018/11/14.
 */
public abstract class AbsDetector implements IDetectorImpl {

    protected ViewVisibilityDetector mParent;

    public AbsDetector(ViewVisibilityDetector parent) {
        mParent = parent;
    }

    protected boolean isEnable() {
        return mParent.enable;
    }

    /**
     * 计算item的可见度：可见区域 / item有效区域的占比
     * @param item
     * @return
     */
    protected float calculateItemVisiblePercentage(DetectableItemView item) {
        final View itemView = item != null ? item.getDetectedView() : null;
        if (itemView == null) {
            return 0;
        }

        // 1.优先使用缓存值
        ViewVisibilityDetector.DetectParams params = mParent.detectParams;
        Map<View, Float> cachedVisibility = params != null ? params.childrenVisibility : null;
        if (cachedVisibility != null && !cachedVisibility.isEmpty()) {
            Float visibility = cachedVisibility.get(itemView);
            if (visibility != null) {
                //VLogger.d(this, itemView + ".visible = " + visibility);
                return visibility;
            }
        }

        // 2.重新计算改View的可见度
        final int top = params != null ? params.topOffset : 0;
        if (top > 0) {
            return calculateVisiblePercentsWithTopOffset(itemView, top);
        } else {
            return calculateVisiblePercents(itemView);
        }
    }
}

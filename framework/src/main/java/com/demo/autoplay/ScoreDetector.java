package com.demo.autoplay;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import static com.demo.autoplay.DetectableItemHelper.activateItem;
import static com.demo.autoplay.DetectableItemHelper.deactivateItem;
import static com.demo.autoplay.DetectableItemHelper.isItemActive;


/**
 * 基于可见度打分进行自动播放的探测器
 * Created by kaige1 on 2018/8/2.
 */
class ScoreDetector extends AutoPlayDetector {

    public ScoreDetector(ViewVisibilityDetector parent) {
        super(parent);
    }

    /**
     * 自动播放规则：
     * <ul>
     *     <li>计算当前界面内所有item的可见度：可见区域 / item有效区域</li>
     *     <li>选取可见度 > 0.5中最大的item，并激活</li>
     *     <li>当活跃item的可见度 < 0.5，反激活</li>
     * </ul>
     *
     * @param viewGroup
     */
    @Override
    public void detect(@NonNull ViewGroup viewGroup) {
        if (!isEnable() || mParent == null) {
            return;
        }

        IViewParser viewParser = mParent.viewParser;
        if (viewParser == null || viewParser.getChildCount() == 0) {
            return;
        }

        float highestScore = 0;
        DetectableItem fitItem = null;
        DetectableItem activeItem = null;

        final int count = viewParser.getChildCount();
        for (int index = 0; index < count; index++) {
            final DetectableItem item = findItemAtIndex(index);

            // 查找当前处于激活状态的item
            if (activeItem == null && isItemActive(item)) {
                activeItem = item;
                // VLogger.d(TAG, "find active item = " + item.getDetectedView());
            }

            // 查找最大可见度的item
            if (checkActivateIfNecessary(item)) {
                final float score = calculateItemVisiblePercentage(item);
                if (score > highestScore) {
                    highestScore = score;
                    fitItem = item;
                    // VLogger.d(TAG, "find fit item = " + item.getDetectedView());
                }
            }
        }

        if (fitItem != null) {
            if (fitItem != activeItem) {
                deactivateItem(activeItem);
                activateItem(fitItem);
            }
        } else {
            if (checkDeactivateIfNecessary(activeItem)) {
                deactivateItem(activeItem);
            }
        }
    }
}

package com.demo.autoplay;

import android.view.View;

import static com.demo.autoplay.DetectableItemHelper.deactivateItem;
import static com.demo.autoplay.DetectableItemHelper.isItemActive;


/**
 * 自动播放探测器
 * Created by kaige1 on 2018/11/14.
 */
public abstract class AutoPlayDetector extends AbsDetector {

    public AutoPlayDetector(ViewVisibilityDetector parent) {
        super(parent);
    }

    @Override
    public void stop() {
        // 如果有活跃的item，反激活之
        final DetectableItem item = findActiveItem();
        if (item != null) {
            deactivateItem(item);
        }
    }

    /**
     * 当前正在活跃状态的Item
     * @return
     */
    protected DetectableItem findActiveItem() {
        final int count = mParent.viewParser.getChildCount();
        for (int index = 0; index < count; index++) {
            final DetectableItem item = findItemAtIndex(index);
            if (isItemActive(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 查找ViewGroup第index位置的ItemView，并且该Item支持探测
     * @param index
     * @return
     */
    protected DetectableItem findItemAtIndex(int index) {
        final View itemView = mParent.viewParser.getChildAt(index);
        if (itemView instanceof DetectableItem) {
            return (DetectableItem) itemView;
        }
        return null;
    }

    /**
     * Item是否需要激活
     * @param item
     * @return
     */
    public final boolean checkActivateIfNecessary(DetectableItem item) {
        if (item == null) {
            return false; //throw NPE!
        }

        final View itemView = item.getDetectedView();
        if (itemView == null) {
            return false;
        }

        // 若item定制了反激活的策略，使用其规则
        if (itemView instanceof DetectRules) {
            return ((DetectRules) itemView).checkActivateIfNecessary();
        }

        if (itemView instanceof DetectRules2) {
            ViewVisibilityDetector.DetectParams params = mParent.detectParams;
            final int top = params != null ? params.topOffset : 0;
            final int bottom = params != null ? params.bottomOffset : 0;
            return ((DetectRules2) itemView).checkActivateIfNecessary(top, bottom);
        }

        // 没有定制时，由具体的探测器实现其规则
        return calculateItemVisiblePercentage(item) > .5f;
    }

    /**
     * Item是否要反激活
     * @param item
     * @return
     */
    public final boolean checkDeactivateIfNecessary(DetectableItem item) {
        if (item == null) {
            return true; //throw NPE!
        }

        final View itemView = item.getDetectedView();
        if (itemView == null) {
            return true;
        }

        // 若item定制了激活的策略，使用其规则
        if (itemView instanceof DetectRules) {
            return ((DetectRules) itemView).checkDeactivateIfNecessary();
        }

        if (itemView instanceof DetectRules2) {
            ViewVisibilityDetector.DetectParams params = mParent.detectParams;
            final int top = params != null ? params.topOffset : 0;
            final int bottom = params != null ? params.bottomOffset : 0;
            return ((DetectRules2) itemView).checkDeactivateIfNecessary(top, bottom);
        }

        // 没有定制时，由具体的探测器实现其规则
        return calculateItemVisiblePercentage(item) < .5f;
    }

}

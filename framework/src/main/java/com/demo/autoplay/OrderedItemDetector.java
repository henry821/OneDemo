package com.demo.autoplay;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import static com.demo.autoplay.DetectableItemHelper.activateItem;
import static com.demo.autoplay.DetectableItemHelper.deactivateItem;
import static com.demo.autoplay.DetectableItemHelper.isItemActive;

/**
 * 探测器规则：第一个满足条件的优先被激活
 * Created by kaige1 on 14/12/2017.
 */
public class OrderedItemDetector extends AutoPlayDetector {

    protected static final int UP   = 1;
    protected static final int DOWN = 2;

    private int mFirstItemIndex; // item的在ViewGroup数据列表中的index
    private int mFirstItemTop;

    public OrderedItemDetector(ViewVisibilityDetector parent) {
        super(parent);
    }

    /**
     * 按照以下规则查找并激活对应的Item
     * <ul>
     *    <li>向上滑动
     *       <ol>
     *           <li>查找当前处于活跃状态的Item</li>
     *           <li>如果没有，从上到下检查第一个可被激活的Item</li>
     *           <li>如果有，检查活跃Item是否满足反激活的条件，满足，反激活该Item，
     *           并从该Item开始，自上向下开始检查第一个可被激活的Item</li>
     *       </ol>
     *    </li>
     *    <li>向下滑动
     *       <ol>
     *           <li>查找当前处于激活状态的Item</li>
     *           <li>自上而下查找一个满足条件的Item，如果这个Item就是当前活跃的，保持活跃状态，否则</li>
     *           <li>如果找到了新的Item，反激活当前活跃的Item，并激活这个</li>
     *           <li>如果没有找到，检查当前活跃的Item是否满足反激活条件，满足则反激活该Item</li>
     *       </ol>
     *    </li>
     * </ul>
     *
     * <p>如果有特殊规则，可重写该方法</p>
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

        // 查找当前的active item
        DetectableItem activeItem = null;
        int activeIndex = -1;
        final int count = mParent.viewParser.getChildCount();
        for (int index = 0; index < count; index++) {
            final DetectableItem item = findItemAtIndex(index);
            if (isItemActive(item)) {
                activeItem = item;
                activeIndex = index;
                break;
            }
        }

        //VLogger.i(this, "detectItem", commonToString(viewGroup), "childCount = " + getChildCount(viewGroup));
        final int direction = detectDirection();
        //VLogger.d(this, "direction", direction2String(direction));
        switch (direction) {
            case UP: {
                //VLogger.d(this, "findActiveItem", commonToString(activeItem));
                if (activeItem != null) {
                    if (checkDeactivateIfNecessary(activeItem)) {
                        deactivateItem(activeItem);
                        DetectableItem item = findItemUpToDown(activeItem);
                        activateItem(item);
                    }
                } else {
                    DetectableItem item = findItemUpToDown(null);
                    activateItem(item);
                }
                break;
            }
            case DOWN: {
                //VLogger.d(this, "findActiveItem", commonToString(activeItem));
                final DetectableItem item = findItemUpToDown(null);
                if (item != null) {
                    if (item != activeItem) {
                        deactivateItem(activeItem);
                        activateItem(item);
                    }
                } else {
                    if (checkDeactivateIfNecessary(activeItem)) {
                        deactivateItem(activeItem);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * 检测当前滑动的方向
     * @return {@link #UP} 向上滚动；{@link #DOWN} 向下滚动
     */
    private int detectDirection() {
        int direction = -1;
        IViewParser viewParser = mParent.viewParser;
        if (viewParser.getChildCount() > 0) {
            final int index = viewParser.getFirstVisiblePosition();
            final View view = viewParser.getChildAt(0);
            final int top = view != null ? view.getTop() : 0;
            if (index == mFirstItemIndex) {
                if (top > mFirstItemTop) {
                    direction = DOWN;
                } else {
                    direction = UP;
                }
            } else {
                if (index > mFirstItemIndex) {
                    direction = UP;
                } else {
                    direction = DOWN;
                }
            }

            mFirstItemIndex = index;
            mFirstItemTop = top;
        }

        return direction;
    }

    /**
     * 从活跃Item的下一个开始，自上而下查找一个满足条件的Item；
     * 如果没有活跃Item，则从第一个开始
     * @param activeItem
     * @return
     */
    private DetectableItem findItemUpToDown(DetectableItem activeItem) {
        IViewParser viewParser = mParent.viewParser;

        // 1.计算当前活跃Item的View index
        int index;
        if (activeItem instanceof View) {
            index = viewParser.indexOfChild((View) activeItem);
            if (index < 0) {
                index = -1;
            }
        } else {
            index = -1; // 当前没有活跃的Item
        }

        // 2. 从index的下一个开始，自上到下查找
        index++;
        //VLogger.v(this, "findNext", "start from index = " + index);

        final int count = viewParser.getChildCount();
        for (int position = index; position < count; position++) {
            // 3. 寻找特定的Item
            final DetectableItem item = findItemAtIndex(position);
            if (item != null) {
                // 4. 检查Item满足激活的条件
                if (checkActivateIfNecessary(item)) {
                    return item;
                }
            }
        }
        return null;
    }
}

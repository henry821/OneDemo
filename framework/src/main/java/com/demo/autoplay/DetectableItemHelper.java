package com.demo.autoplay;

import android.view.View;

import com.baselibrary.R;

import static com.demo.autoplay.DetectorFactory.invokeItem;

/**
 * {@link DetectableItem}的工具
 * Created by kaige1 on 22/01/2018.
 */
public class DetectableItemHelper {
    /**
     * 检查Item是否处于被激活的状态
     *
     * @param item
     * @return
     */
    /*package*/
    static boolean isItemActive(DetectableItemView item) {
        final View view = item != null ? item.getDetectedView() : null;
        if (view != null) {
            boolean active;
            final Object state = view.getTag(R.id.detectable_item_active_state);
            active = state != null && (Boolean) state;
            return active;
        }
        return false;
    }

    /**
     * 对目标Item的View标记一个特殊的tag，表示激活状态
     *
     * @param item
     * @param state
     */
    /*package*/
    static void markItemActiveState(String type, DetectableItemView item, boolean state) {
        final View view = item.getDetectedView();
        if (view != null) {
            switch (type) {
                case AutoPlayManager.AUTO_PLAY_ITEM:
                    view.setTag(R.id.detectable_item_active_state, state);
                    break;
                case AutoPlayManager.EXPOSURE_ITEM:
                    view.setTag(R.id.detectable_item_active_state_exposure, state);
                    break;
            }

        }
    }

    /**
     * 对目标Item的View标记一个特殊的tag，表示激活状态
     *
     * @param item
     * @param state
     */
    /*package*/
    public static void markItemActiveState(DetectableItemView item, boolean state) {
        final View view = item.getDetectedView();
        if (view != null) {
            view.setTag(R.id.detectable_item_active_state, state);
        }
    }

    /**
     * 激活Item
     *
     * @param item
     */
    public static void activateItem(DetectableItemView item) {
        if (item != null) {
            markItemActiveState(AutoPlayManager.AUTO_PLAY_ITEM, item, true);
            invokeItem(AutoPlayManager.AUTO_PLAY_ITEM, item, true);
        }
    }

    /**
     * 激活Item
     *
     * @param item
     */
    public static void activateItem(String type, DetectableItemView item) {
        if (item != null) {
            markItemActiveState(type, item, true);
            invokeItem(type, item, true);
        }
    }

    /**
     * 反激活Item
     *
     * @param item
     */
    public static void deactivateItem(DetectableItemView item) {
        if (item != null) {
            markItemActiveState(AutoPlayManager.AUTO_PLAY_ITEM, item, false);
            invokeItem(AutoPlayManager.AUTO_PLAY_ITEM, item, false);
        }
    }

    /**
     * 反激活Item
     *
     * @param item
     */
    public static void deactivateItem(String type, DetectableItemView item) {
        if (item != null) {
            markItemActiveState(type, item, false);
            invokeItem(type, item, false);
        }
    }
}

package com.demo.autoplay;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import static com.demo.autoplay.AutoPlayManager.AUTO_PLAY_ITEM;

/**
 * 对不同的{@link View}进行适配，生成具体的探测器
 * <p>
 * Created by kaige1 on 28/07/2017.
 */
public class DetectorFactory {

    public static ViewVisibilityDetector makeDetector(@NonNull ViewGroup viewGroup) {
        return makeDetector(viewGroup, 0, 0);
    }

    public static ViewVisibilityDetector makeDetector(@NonNull ViewGroup viewGroup, int topOffset, int bottomOffset) {
        final IViewParser parser = makeParser(viewGroup);
        return new ViewVisibilityDetector(parser, topOffset, bottomOffset);
    }

    /**
     * 针对不同的ViewGroup进行适配
     *
     * @param viewGroup
     * @return
     */
    /*package*/
    static IViewParser makeParser(ViewGroup viewGroup) {
        if (viewGroup == null) {
            throw new NullPointerException("Cannot detect on a NULL ViewGroup");
        }

        if (viewGroup instanceof ListView) {
            return new ListViewParser((ListView) viewGroup);
        }

        if (viewGroup instanceof RecyclerView) {

            if (((RecyclerView) viewGroup).getLayoutManager() instanceof StaggeredGridLayoutManager) {
                return new StaggeredGridLayoutManagerParser(((RecyclerView) viewGroup));
            }

            return new RecyclerViewParser(((RecyclerView) viewGroup));
        }
        if (viewGroup instanceof ViewPager) {
            return new ViewPagerParser(((ViewPager) viewGroup));
        }
        if (viewGroup instanceof LinearLayout) {
            return new SimpleViewParser(viewGroup);
        }

        throw new IllegalArgumentException(viewGroup.getClass().toString() + " is NOT support");
    }

    /**
     * 针对不同的业务实现不同规则的探测器
     *
     * @param parent
     * @param itemType
     * @return
     */
    /*package*/
    static IDetectorImpl makeImpl(ViewVisibilityDetector parent, String itemType) {
        if (parent == null || TextUtils.isEmpty(itemType)) {
            return null;
        }

        switch (itemType) {
            case AUTO_PLAY_ITEM: {
                return new OrderedItemDetector(parent);
            }
            default:
                return null;
        }
    }

    /**
     * 针对不同的业务，定义item协议
     *
     * @param item
     * @param activeOrNot
     */
    /*package*/
    static void invokeItem(DetectableItemView item, boolean activeOrNot) {
        if (item != null) {
            if (item instanceof DetectableItem) {
                if (activeOrNot) {
                    ((DetectableItem) item).activate();
                } else {
                    ((DetectableItem) item).deactivate();
                }
            }
        }
    }

    /**
     * 针对不同的业务，定义item协议
     *
     * @param item
     * @param activeOrNot
     */
    /*package*/
    static void invokeItem(String type, DetectableItemView item, boolean activeOrNot) {
        if (item == null) {
            return;
        }
        switch (type) {
            case AUTO_PLAY_ITEM:
                if (item instanceof DetectableItem) {
                    if (activeOrNot) {
                        ((DetectableItem) item).activate();
                    } else {
                        ((DetectableItem) item).deactivate();
                    }
                }
                break;
        }

    }
}

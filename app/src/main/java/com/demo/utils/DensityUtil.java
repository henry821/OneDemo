package com.demo.utils;

import android.content.Context;

/**
 * Description 屏幕密度工具类
 * Author wanghengwei
 * Date   2019/3/22 19:59
 */
public class DensityUtil {

    /**
     * 屏幕密度系数 1.5f    4f
     */
    private static float density;

    /**
     * dp to px
     */
    public static int dip2px(Context context, float dipValue) {
        if (density == 0) {
            density = getScreenDensity(context);
        }
        return (int) (dipValue * density + 0.5f);
    }

    /**
     * 得到设备的密度 1.5f 2.0f 3.0f
     */
    private static float getScreenDensity(Context context) {
        if (density == 0) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }
}

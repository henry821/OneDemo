package com.demo.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Description Intent跳转
 * <br>Author wanghengwei
 * <br>Date   2018/10/31 10:41
 */
public class IntentUtil {

    public static void gotoActivity(Context start, Class end, Bundle bundle) {
        Intent intent = new Intent(start, end);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        start.startActivity(intent);
    }
}

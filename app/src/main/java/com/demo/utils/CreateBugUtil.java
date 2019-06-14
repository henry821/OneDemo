package com.demo.utils;

import android.content.Context;
import android.widget.Toast;

import com.demo.one.R;

/**
 * Description 产生一个运行时错误，用于测试热修复
 * Author wanghengwei
 * Date   2019/6/14 16:52
 */
public class CreateBugUtil {

    public static void createBug(Context context) {
        Toast.makeText(context, R.string.hot_fix_before, Toast.LENGTH_SHORT).show();
    }

}

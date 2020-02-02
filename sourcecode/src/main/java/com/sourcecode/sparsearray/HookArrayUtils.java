package com.sourcecode.sparsearray;

import android.annotation.SuppressLint;

import com.demo.one.base.utils.LogUtil;

import java.lang.reflect.Method;

/**
 * Description 使用反射方式调用ArrayUtils方法
 * Author wanghengwei
 * Date   2019/7/26 17:00
 */
class HookArrayUtils {

    @SuppressLint("PrivateApi")
    static Object newUnpaddedObjectArray(int minLen) {
        try {
            Class<?> cls;
            cls = Class.forName("com.android.internal.util.ArrayUtils");
            Method method = cls.getDeclaredMethod("newUnpaddedObjectArray", int.class);
            return method.invoke(null, minLen);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return null;
    }

    @SuppressLint("PrivateApi")
    static Object newUnpaddedIntArray(int minLen) {
        try {
            Class<?> cls;
            cls = Class.forName("com.android.internal.util.ArrayUtils");
            Method method = cls.getDeclaredMethod("newUnpaddedIntArray", int.class);
            return method.invoke(null, minLen);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return null;
    }

    @SuppressLint("PrivateApi")
    static Object newUnpaddedArray(Class clazz, int minLen) {
        try {
            Class<?> cls;
            cls = Class.forName("com.android.internal.util.ArrayUtils");
            Method method = cls.getDeclaredMethod("newUnpaddedArray", Class.class, int.class);
            return method.invoke(null, clazz, minLen);
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return null;
    }
}

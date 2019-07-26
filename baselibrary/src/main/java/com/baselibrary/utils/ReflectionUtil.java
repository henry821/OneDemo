package com.baselibrary.utils;

import java.lang.reflect.Field;

/**
 * Description 反射工具类
 * Author wanghengwei
 * Date   2019/7/26 15:28
 */
public class ReflectionUtil {

    /**
     * 获取变量
     *
     * @param obj 当前类实例
     * @param cls 当前类Class对象
     * @param str 变量名
     * @return 需要获取的变量
     */
    public static Object getField(Object obj, Class cls, String str)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    /**
     * 给变量设置新值
     *
     * @param obj      当前类实例
     * @param cls      当前类Class对象
     * @param str      变量名
     * @param newValue 需要赋值的新值
     */
    public static void setField(Object obj, Class cls, String str, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, newValue);
    }

}

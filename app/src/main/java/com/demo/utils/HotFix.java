package com.demo.utils;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Description
 * Author wanghengwei
 * Date   2019/6/14 14:47
 */
public class HotFix {

    public static void fix(Context context, String fixDexFilePath)
            throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        injectDexToClassLoader(context, fixDexFilePath);
    }

    private static void injectDexToClassLoader(Context context, String fixDexFilePath)
            throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        //读取baseElements
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        Object basePathList = getPathList(pathClassLoader);
        Object baseElements = getDexElements(basePathList);

        //读取fixElements
        String baseDexAbsolutePath = context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
        DexClassLoader fixDexClassLoader = new DexClassLoader(fixDexFilePath,
                baseDexAbsolutePath, fixDexFilePath, context.getClassLoader());
        Object fixPathList = getPathList(fixDexClassLoader);
        Object fixElements = getDexElements(fixPathList);

        //合并两份Elements
        Object newElements = combineArray(baseElements, fixElements);

        //一定要重新获取，不要用basePathList，会报错
        Object basePathList2 = getPathList(pathClassLoader);

        //把新的dexElements对象设置回去
        setField(basePathList2, basePathList2.getClass(), "dexElements", newElements);

    }

    /**
     * 通过反射获取pathList对象
     *
     * @param obj 当前类实例
     * @return pathList对象
     */
    private static Object getPathList(Object obj)
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(obj, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /**
     * 从获取到的pathList对象中，进一步反射获得dexElements对象
     *
     * @param obj 当前类实例
     * @return dexElements对象
     */
    private static Object getDexElements(Object obj)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, obj.getClass(), "dexElements");
    }

    /**
     * 获取变量
     *
     * @param obj 当前类实例
     * @param cls 当前类Class对象
     * @param str 变量名
     * @return 需要获取的变量
     */
    private static Object getField(Object obj, Class cls, String str)
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
    private static void setField(Object obj, Class cls, String str, Object newValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, newValue);
    }

    /**
     * 合并dexElements，并确保fixElements在baseElements之前
     *
     * @param baseElements 原dex的dexElements
     * @param fixElements  修复dex的dexElements
     * @return 新dexElements
     */
    private static Object combineArray(Object baseElements, Object fixElements) {
        Class<?> componentType = fixElements.getClass().getComponentType();
        int length = Array.getLength(fixElements);
        int length2 = Array.getLength(baseElements) + length;
        Object newInstance = Array.newInstance(componentType, length2);
        for (int i = 0; i < length2; i++) {
            if (i < length) {
                Array.set(newInstance, i, Array.get(fixElements, i));
            } else {
                Array.set(newInstance, i, Array.get(baseElements, i - length));
            }
        }
        return newInstance;
    }
}

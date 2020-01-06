package com.demo.beans;

import android.os.Build;

import com.baselibrary.utils.LogUtil;
import com.demo.utils.ReflectUtils;
import com.demo.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;

/**
 * Description 用于替换系统ClassLoader
 * Author wanghengwei
 * Date   2020/1/6
 */
public class HookedClassLoader extends PathClassLoader {

    private final ClassLoader mOrig;

    private Method findResourceMethod;

    private Method findResourcesMethod;

    private Method findLibraryMethod;

    private Method getPackageMethod;

    public HookedClassLoader(ClassLoader parent, ClassLoader orig) {

        // 由于PathClassLoader在初始化时会做一些Dir的处理，所以这里必须要传一些内容进来
        // 但我们最终不用它，而是拷贝所有的Fields
        super("", "", parent);
        mOrig = orig;

        // 将原来宿主里的关键字段，拷贝到这个对象上，这样骗系统以为用的还是以前的东西（尤其是DexPathList）
        // 注意，这里用的是“浅拷贝”
        copyFromOriginal(orig);

        initMethods(orig);
    }

    private void initMethods(ClassLoader cl) {
        Class<?> c = cl.getClass();
        findResourceMethod = ReflectUtils.getMethod(c, "findResource", String.class);
        findResourceMethod.setAccessible(true);
        findResourcesMethod = ReflectUtils.getMethod(c, "findResources", String.class);
        findResourcesMethod.setAccessible(true);
        findLibraryMethod = ReflectUtils.getMethod(c, "findLibrary", String.class);
        findLibraryMethod.setAccessible(true);
        getPackageMethod = ReflectUtils.getMethod(c, "getPackage", String.class);
        getPackageMethod.setAccessible(true);
    }

    private void copyFromOriginal(ClassLoader orig) {
        LogUtil.printClassLoaderD("copyFromOriginal: Fields=" + StringUtils.toStringWithLines(ReflectUtils.getAllFieldsList(orig.getClass())));

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Android 2.2 - 2.3.7，有一堆字段，需要逐一复制
            // 以下方法在较慢的手机上用时：8ms左右
            copyFieldValue("libPath", orig);
            copyFieldValue("libraryPathElements", orig);
            copyFieldValue("mDexs", orig);
            copyFieldValue("mFiles", orig);
            copyFieldValue("mPaths", orig);
            copyFieldValue("mZips", orig);
        } else {
            // Android 4.0以上只需要复制pathList即可
            // 以下方法在较慢的手机上用时：1ms
            copyFieldValue("pathList", orig);
        }
    }

    private void copyFieldValue(String field, ClassLoader orig) {
        try {
            Field f = ReflectUtils.getField(orig.getClass(), field);
            if (f == null) {
                LogUtil.printClassLoaderD("rpcl.cfv: null! f=" + field);
                return;
            }

            // 删除final修饰符
            ReflectUtils.removeFieldFinalModifier(f);

            // 复制Field中的值到this里
            Object o = ReflectUtils.readField(f, orig);
            ReflectUtils.writeField(f, this, o);

            Object test = ReflectUtils.readField(f, this);
            LogUtil.printClassLoaderD("copyFieldValue: Copied. f=" + field + "; actually=" + test + "; orig=" + o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        LogUtil.printClassLoaderE("loadClass: " + name);
        return mOrig.loadClass(name);
    }
}

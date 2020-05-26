package com.demo.skin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Description 加载皮肤资源对象
 * Author wanghengwei
 * Date   2020/5/23
 */
public class SkinManager {

    //Application Context
    private Context context;
    //资源包包名
    private String packageName;


    /*********************** 单例写法 ***********************/

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return SkinManagerHolder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context Application Context
     */
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 根据路径去加载皮肤资源包
     *
     * @param path 皮肤资源包路径
     */
    public void loadSkinApk(String path) {
        //获取到包管理器(包管理器在一个进程中是唯一的)
        PackageManager packageManager = context.getPackageManager();
        //获取资源包的包信息
        PackageInfo packageArchiveInfo =
                packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        packageName = packageArchiveInfo.packageName;
    }

    private static class SkinManagerHolder {
        @SuppressLint("StaticFieldLeak")
        private static final SkinManager INSTANCE = new SkinManager();
    }
}

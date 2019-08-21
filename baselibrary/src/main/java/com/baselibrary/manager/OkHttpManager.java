package com.baselibrary.manager;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Description OkHttp管理类
 * Author wanghengwei
 * Date   2019/8/21 10:15
 */
public class OkHttpManager {

    private static OkHttpClient sClient;

    public static OkHttpManager getInstance() {
        return OkHttpManagerHolder.INSTANCE;
    }

    public OkHttpClient getOkHttpClient() {
        return sClient;
    }

    private OkHttpManager() {
        sClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private static final class OkHttpManagerHolder {
        private static final OkHttpManager INSTANCE = new OkHttpManager();
    }

}

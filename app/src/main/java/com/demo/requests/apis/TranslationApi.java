package com.demo.requests.apis;

import com.demo.beans.TranslationBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Description 获取翻译api
 * Author wanghengwei
 * Date   2019/7/2 17:58
 */

public interface TranslationApi {

    @GET("ajax.php")
    public Call<TranslationBean> getTranslation(@QueryMap HashMap<String, String> hashMap);
}

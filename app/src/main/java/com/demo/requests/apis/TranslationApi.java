package com.demo.requests.apis;

import com.demo.beans.TranslationBean;

import java.util.HashMap;

import io.reactivex.Observable;
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
    Call<TranslationBean> getTranslation(@QueryMap HashMap<String, String> hashMap);

    @GET("ajax.php")
    Observable<TranslationBean> getTranslationByRetrofit(@QueryMap HashMap<String, String> hashMap);

}

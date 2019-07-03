package com.demo.requests;

import com.baselibrary.utils.LogUtil;
import com.demo.beans.TranslationBean;
import com.demo.requests.apis.TranslationApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description 获取翻译Request
 * Author wanghengwei
 * Date   2019/7/2 18:06
 */
public class TranslationRequest {

    // 参数说明：
    // a：固定值 fy
    // f：原文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
    // t：译文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
    // w：查询内容
    public void getTranslation(String word) {

        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("a", "fy");
        queryMap.put("f", "auto");
        queryMap.put("t", "auto");
        queryMap.put("w", word);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TranslationApi translationApi = retrofit.create(TranslationApi.class);
        Call<TranslationBean> call = translationApi.getTranslation(queryMap);
        call.enqueue(new Callback<TranslationBean>() {
            @Override
            public void onResponse(Call<TranslationBean> call, Response<TranslationBean> response) {
                LogUtil.e(response.body().toString());
            }

            @Override
            public void onFailure(Call<TranslationBean> call, Throwable t) {
                LogUtil.printError(t);
            }
        });
    }
}
package com.demo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.demo.requests.TranslationRequest;

public class RetrofitFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslationRequest request = new TranslationRequest();
//        request.getTranslation("hello world");
        request.getTranslationByRetrofit("hello world");
    }
}

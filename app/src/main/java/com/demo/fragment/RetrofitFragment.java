package com.demo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.requests.TranslationRequest;

public class RetrofitFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslationRequest request = new TranslationRequest();
//        request.getTranslation("hello world");
        request.getTranslationByRetrofit("hello world");
    }
}

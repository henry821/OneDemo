package com.demo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.one.R;
import com.demo.requests.TranslationRequest;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        TranslationRequest request = new TranslationRequest();
//        request.getTranslation("hello world");
        request.getTranslationByRetrofit("hello world");
    }
}
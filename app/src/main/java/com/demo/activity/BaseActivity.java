package com.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Description Activity基类
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 17:04
 */
public abstract class BaseActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentViewId());
        initData();
        initView();
    }

    abstract int getContentViewId();

    abstract void initData();

    abstract void initView();
}

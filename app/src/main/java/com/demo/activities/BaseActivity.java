package com.demo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baselibrary.utils.LogUtil;

/**
 * Description
 * Author wanghengwei
 * Date   2019/8/9 10:25
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.v(getClass().getSimpleName() + "---onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.v(getClass().getSimpleName() + "---onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.v(getClass().getSimpleName() + "---onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.v(getClass().getSimpleName() + "---onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.v(getClass().getSimpleName() + "---onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.v(getClass().getSimpleName() + "---onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.v(getClass().getSimpleName() + "---onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.v(getClass().getSimpleName() + "---onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.v(getClass().getSimpleName() + "---onRestoreInstanceState");
    }
}

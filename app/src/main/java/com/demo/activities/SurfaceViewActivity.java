package com.demo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.demo.one.R;

/**
 * Description SurfaceViewActivity
 * <br>Author wanghengwei
 * <br>Date   2018/11/19 17:12
 */
public class SurfaceViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_surface_view);
    }
}

package com.demo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ActivityMainBinding;
import com.demo.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TitleBean> mTitleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initTitleList();
        TitleListDataBindingAdapter adapter = new TitleListDataBindingAdapter(this, mTitleList, new TitleListDataBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TitleBean bean) {
                IntentUtil.gotoActivity(MainActivity.this, bean.getToPkgClazz(), null);
            }
        });

        binding.rvTitle.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTitle.setAdapter(adapter);
    }

    private void initTitleList() {
        mTitleList = new ArrayList<>();
        mTitleList.add(new TitleBean("SurfaceView显示图片", SurfaceViewActivity.class));
        mTitleList.add(new TitleBean("RxJava2源码学习", RxJavaLearningActivity.class));
        mTitleList.add(new TitleBean("Kotlin学习", KotlinLearningActivity.class));
    }

}

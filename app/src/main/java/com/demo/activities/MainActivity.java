package com.demo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.baselibrary.utils.IntentUtil;
import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ActivityMainBinding;

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
        mTitleList.add(new TitleBean("RxJava2源码学习", RxJavaActivity.class));
        mTitleList.add(new TitleBean("Retrofit2学习", RetrofitActivity.class));
        mTitleList.add(new TitleBean("Kotlin学习", KotlinActivity.class));
        mTitleList.add(new TitleBean("热更新示例", HotFixActivity.class));
        mTitleList.add(new TitleBean("SparseArray学习", SparseArrayActivity.class));
        mTitleList.add(new TitleBean("RecyclerView学习", RecyclerViewActivity.class));
        mTitleList.add(new TitleBean("EventBus学习", EventBusActivity.class));
        mTitleList.add(new TitleBean("Espresso学习", EspressoActivity.class));
        mTitleList.add(new TitleBean("LeakCanary学习", LeakCanaryActivity.class));
        mTitleList.add(new TitleBean("试验页面", TestActivity.class));
    }

}

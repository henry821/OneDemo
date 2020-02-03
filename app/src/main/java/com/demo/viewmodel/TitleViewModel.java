package com.demo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.activities.BlockCanaryActivity;
import com.demo.activities.EspressoActivity;
import com.demo.activities.EventBusActivity;
import com.demo.activities.HotFixActivity;
import com.demo.activities.JniActivity;
import com.demo.activities.KotlinActivity;
import com.demo.activities.LeakCanaryActivity;
import com.demo.activities.RecyclerViewActivity;
import com.demo.activities.RetrofitActivity;
import com.demo.activities.RxJavaActivity;
import com.demo.activities.ServiceFragment;
import com.demo.activities.SparseArrayActivity;
import com.demo.activities.TestActivity;
import com.demo.beans.TitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 主页导航栏数据
 * Author wanghengwei
 * Date   2020/2/3
 */
public class TitleViewModel extends ViewModel {

    private MutableLiveData<List<TitleBean>> mTitles;

    public TitleViewModel() {
        initTitles();
    }

    public LiveData<List<TitleBean>> getTitles() {
        return mTitles;
    }

    private void initTitles() {
        mTitles = new MutableLiveData<>();

        List<TitleBean> titleList = new ArrayList<>();
        titleList.add(new TitleBean("Service学习", ServiceFragment.class));
        titleList.add(new TitleBean("RxJava2源码学习", RxJavaActivity.class));
        titleList.add(new TitleBean("Retrofit2学习", RetrofitActivity.class));
        titleList.add(new TitleBean("Kotlin学习", KotlinActivity.class));
        titleList.add(new TitleBean("热更新示例", HotFixActivity.class));
        titleList.add(new TitleBean("SparseArray学习", SparseArrayActivity.class));
        titleList.add(new TitleBean("RecyclerView学习", RecyclerViewActivity.class));
        titleList.add(new TitleBean("EventBus学习", EventBusActivity.class));
        titleList.add(new TitleBean("Espresso学习", EspressoActivity.class));
        titleList.add(new TitleBean("LeakCanary学习", LeakCanaryActivity.class));
        titleList.add(new TitleBean("BlockCanary学习", BlockCanaryActivity.class));
        titleList.add(new TitleBean("Jni学习", JniActivity.class));
        titleList.add(new TitleBean("试验页面", TestActivity.class));

        mTitles.setValue(titleList);
    }

}

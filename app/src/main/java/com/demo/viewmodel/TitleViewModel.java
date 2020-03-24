package com.demo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.beans.TitleBean;
import com.demo.fragment.JniFragment;
import com.demo.fragment.LeakCanaryFragment;
import com.demo.fragment.RecyclerViewFragment;
import com.demo.fragment.RetrofitFragment;
import com.demo.fragment.ServiceFragment;
import com.demo.fragment.SparseArrayFragment;
import com.demo.fragment.TestFragment;

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
        titleList.add(new TitleBean("RxJava2源码学习", RxJavaFragment.class));
        titleList.add(new TitleBean("Retrofit2学习", RetrofitFragment.class));
        titleList.add(new TitleBean("SparseArray学习", SparseArrayFragment.class));
        titleList.add(new TitleBean("RecyclerView学习", RecyclerViewFragment.class));
        titleList.add(new TitleBean("LeakCanary学习", LeakCanaryFragment.class));
        titleList.add(new TitleBean("Jni学习", JniFragment.class));
        titleList.add(new TitleBean("试验页面", TestFragment.class));

        mTitles.setValue(titleList);
    }

}

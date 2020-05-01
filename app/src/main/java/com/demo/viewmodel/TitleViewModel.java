package com.demo.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.beans.TitleBean;
import com.demo.one.R;

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
        titleList.add(new TitleBean("Service学习", R.id.action_mainFragment_to_serviceFragment));
        titleList.add(new TitleBean("LaunchMode学习", R.id.action_mainFragment_to_launchModeFragment));
        titleList.add(new TitleBean("Retrofit2学习", R.id.action_mainFragment_to_retrofitFragment));
        titleList.add(new TitleBean("SparseArray学习", R.id.action_mainFragment_to_sparseArrayFragment));
        titleList.add(new TitleBean("RecyclerView学习", R.id.action_mainFragment_to_recyclerViewFragment));
        titleList.add(new TitleBean("LeakCanary学习", R.id.action_mainFragment_to_leakCanaryFragment));
        titleList.add(new TitleBean("Jni学习", R.id.action_mainFragment_to_jniFragment));
        titleList.add(new TitleBean("试验页面", R.id.action_mainFragment_to_testFragment));

        mTitles.setValue(titleList);
    }

}

package com.demo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Description 记录Service绑定状态的ViewModel
 * Author wanghengwei
 * Date   2020/2/3
 */
public class ServiceStatusViewModel extends ViewModel {

    private MutableLiveData<Boolean> mBindStatus;

    public ServiceStatusViewModel() {
        mBindStatus = new MutableLiveData<>();
        mBindStatus.setValue(false);
    }


    public MutableLiveData<Boolean> getBindStatus() {
        return mBindStatus;
    }
}

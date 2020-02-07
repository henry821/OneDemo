package com.demo.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;

import com.demo.one.R;
import com.demo.one.base.utils.LogUtil;
import com.demo.one.databinding.FragmentServiceBinding;
import com.demo.service.BindModeService;
import com.demo.service.StartModeService;
import com.demo.viewmodel.ServiceStatusViewModel;

import static android.content.Context.BIND_AUTO_CREATE;

public class ServiceFragment extends Fragment implements LifecycleObserver {

    private ServiceConnection mConnection;
    private BindModeService.MyBinder mBinder;

    private ServiceStatusViewModel mStatusViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatusViewModel = ViewModelProviders.of(this).get(ServiceStatusViewModel.class);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.e(getClass().getName() + "与" + name.getClass().getName() + "建立了连接");
                mStatusViewModel.getBindStatus().setValue(true);
                mBinder = (BindModeService.MyBinder) service;
                mBinder.execute();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.e(getClass().getName() + "与" + name.getClass().getName() + "断开了连接");
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentServiceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);
        binding.setOnClickListener(this);
        return binding.getRoot();
    }

    public void startAService() {
        getActivity().startService(new Intent(getContext(), StartModeService.class));
    }

    public void bindAService() {
        getActivity().bindService(new Intent(getContext(), BindModeService.class)
                , mConnection, BIND_AUTO_CREATE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearAllServices() {
        getActivity().stopService(new Intent(getContext(), StartModeService.class));
        if (mStatusViewModel.getBindStatus().getValue() == null) {
            return;
        }
        if (mStatusViewModel.getBindStatus().getValue()) {
            getActivity().unbindService(mConnection);
        }
    }

}

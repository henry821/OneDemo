package com.sunlive.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunlive.R;
import com.sunlive.base.BaseActivity;
import com.sunlive.data.LiveInfo;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private LiveListAdapter mAdapter;

    private HomeContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this);

        RecyclerView rvContent = findViewById(R.id.rv_content);
        mAdapter = new LiveListAdapter(new ArrayList<LiveInfo>());
        rvContent.setAdapter(mAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showLiveInfo(List<LiveInfo> liveInfoList) {
        mAdapter.setDataList(liveInfoList);
    }

    @Override
    public void showLoading() {
        Log.e("whw", "showLoadingView: ");
    }

    @Override
    public void hideLoading() {
        Log.e("whw", "hideLoadingView: ");
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

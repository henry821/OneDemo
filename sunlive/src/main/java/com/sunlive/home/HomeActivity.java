package com.sunlive.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunlive.R;
import com.sunlive.base.BaseActivity;
import com.sunlive.base.IBasePresenter;
import com.sunlive.base.IBaseView;

public class HomeActivity extends BaseActivity implements IBaseView<LiveInfo> {

    private RecyclerView rvContent;
    private LiveListAdapter adapter;

    private IBasePresenter<LiveInfo> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvContent = findViewById(R.id.rv_content);

        presenter = new HomePresenter(this);
        presenter.loadDataFromLocal();
    }

    @Override
    public void showData(LiveInfo liveInfo) {
        Log.e("whw", "showData: "+liveInfo.toString() );
        if (adapter == null) {
            adapter = new LiveListAdapter(liveInfo.getDataList());
            rvContent.setAdapter(adapter);
            rvContent.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}

package com.sunlive.home;

import android.util.Log;

import com.sunlive.data.LiveInfo;
import com.sunlive.data.source.LiveInfoDataSource;
import com.sunlive.data.source.LiveInfoRepository;
import com.sunlive.data.source.local.LiveInfoLocalDataSource;
import com.sunlive.data.source.remote.LiveInfoRemoteDataSource;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;
    private LiveInfoRepository mRepository;

    private boolean mFirstLoad = true;

    public HomePresenter(HomeContract.View homeView) {
        mRepository = LiveInfoRepository.getInstance(LiveInfoRemoteDataSource.getInstance(),
                LiveInfoLocalDataSource.getInstance());
        mHomeView = homeView;
        mHomeView.setPresenter(this);

    }

    @Override
    public void start() {
        loadLiveInfo(false);
    }


    @Override
    public void loadLiveInfo(boolean forceUpdate) {
        loadLiveInfo(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadLiveInfo(boolean forceUpdate, boolean showLoadingUI) {
        if (showLoadingUI) {
            mHomeView.showLoading();
        }
        if (forceUpdate) {
            mRepository.reset();
        }
        mRepository.getLiveInfoList(new LiveInfoDataSource.GetLiveInfoCallback() {
            @Override
            public void onLiveInfoLoaded(List<LiveInfo> dataList) {
                mHomeView.showLiveInfo(dataList);
                mHomeView.hideLoading();
            }

            @Override
            public void onDataNotAvailable() {
                mHomeView.hideLoading();
            }
        });
    }

}

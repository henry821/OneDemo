package com.sunlive.home;

import com.sunlive.base.IBaseModel;
import com.sunlive.base.IBasePresenter;
import com.sunlive.base.IBaseView;

public class HomePresenter implements IBasePresenter<LiveInfo> {

    private IBaseView<LiveInfo> homeView;
    private IBaseModel homeModel;

    public HomePresenter(IBaseView<LiveInfo> baseView) {
        this.homeView = baseView;
        homeModel = new HomeModel(this);

    }

    @Override
    public void loadDataFromNet() {

    }

    @Override
    public void loadDataFromLocal() {
        homeModel.getDataFromLocal();
    }

    @Override
    public void onPreLoad() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadSuccess(LiveInfo liveInfo) {
        homeView.hideLoadingView();
        homeView.showData(liveInfo);
    }


    @Override
    public void onLoadFail() {

    }
}

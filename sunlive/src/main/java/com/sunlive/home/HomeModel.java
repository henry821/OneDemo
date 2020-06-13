package com.sunlive.home;

import com.sunlive.base.IBaseModel;
import com.sunlive.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeModel implements IBaseModel {

    private IBasePresenter<LiveInfo> presenter;

    private LiveInfo liveInfo;

    public HomeModel(IBasePresenter<LiveInfo> presenter) {
        this.presenter = presenter;

        List<LiveInfo.LiveInfoDetail> liveInfoList = new ArrayList<>();
        liveInfoList.add(new LiveInfo.LiveInfoDetail("","第一项数据"));
        liveInfoList.add(new LiveInfo.LiveInfoDetail("","第二项数据"));
        liveInfoList.add(new LiveInfo.LiveInfoDetail("","第三项数据"));
        liveInfoList.add(new LiveInfo.LiveInfoDetail("","第四项数据"));
        liveInfoList.add(new LiveInfo.LiveInfoDetail("","第五项数据"));

        liveInfo = new LiveInfo(1, liveInfoList);
    }

    @Override
    public void getDataFromLocal() {
        presenter.onLoadSuccess(liveInfo);
    }

    @Override
    public void getDataFromNet(int page) {

    }
}

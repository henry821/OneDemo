package com.sunlive.home;

import com.sunlive.base.BasePresenter;
import com.sunlive.base.BaseView;
import com.sunlive.data.LiveInfo;

import java.util.List;

/**
 * Created by hengwei on 2020/6/15.
 */
public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showLiveInfo(List<LiveInfo> liveInfoList);

        void showLoading();

        void hideLoading();

    }

    interface Presenter extends BasePresenter {

        void loadLiveInfo(boolean forceUpdate);

    }

}

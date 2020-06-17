package com.sunlive.data.source;

import androidx.annotation.NonNull;

import com.sunlive.data.LiveInfo;

import java.util.List;

/**
 * Created by hengwei on 2020/6/15.
 */
public interface LiveInfoDataSource {

    interface GetLiveInfoCallback {

        void onLiveInfoLoaded(List<LiveInfo> dataList);

        void onDataNotAvailable();

    }

    void reset();

    void getLiveInfoList(@NonNull GetLiveInfoCallback callback);

    LiveInfo getLiveInfo(String rid);

    void saveLiveInfo(LiveInfo liveInfo);

    void deleteAllLiveInfo();

}

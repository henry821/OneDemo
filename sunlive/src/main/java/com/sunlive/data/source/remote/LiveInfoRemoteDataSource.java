package com.sunlive.data.source.remote;


import android.os.Handler;

import androidx.annotation.NonNull;

import com.sunlive.data.LiveInfo;
import com.sunlive.data.source.LiveInfoDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengwei on 2020/6/15.
 */
public class LiveInfoRemoteDataSource implements LiveInfoDataSource {

    private static List<LiveInfo> sMockRemoteData;

    private LiveInfoRemoteDataSource() {

        sMockRemoteData = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            LiveInfo liveInfo = new LiveInfo(String.valueOf(i), "", String.format("第%s项数据", i));
            liveInfo.setTag("远端仓库");
            sMockRemoteData.add(liveInfo);
        }

    }

    @Override
    public void reset() {

    }

    @Override
    public void getLiveInfoList(@NonNull final GetLiveInfoCallback callback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLiveInfoLoaded(sMockRemoteData);
            }
        }, 5000);
    }

    @Override
    public LiveInfo getLiveInfo(String rid) {
        for (LiveInfo sMockRemoteDatum : sMockRemoteData) {
            if (sMockRemoteDatum.getTag().equals(rid)) {
                return sMockRemoteDatum;
            }
        }
        return null;
    }

    @Override
    public void saveLiveInfo(LiveInfo liveInfo) {
        sMockRemoteData.add(liveInfo);
    }

    @Override
    public void deleteAllLiveInfo() {

    }

    public static LiveInfoRemoteDataSource getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final LiveInfoRemoteDataSource INSTANCE = new LiveInfoRemoteDataSource();
    }

}

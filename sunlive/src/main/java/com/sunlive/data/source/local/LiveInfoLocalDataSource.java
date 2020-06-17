package com.sunlive.data.source.local;

import androidx.annotation.NonNull;

import com.sunlive.data.LiveInfo;
import com.sunlive.data.source.LiveInfoDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengwei on 2020/6/15.
 */
public class LiveInfoLocalDataSource implements LiveInfoDataSource {

    private static List<LiveInfo> sMockLocalData;

    private LiveInfoLocalDataSource() {
        sMockLocalData = new ArrayList<>();
    }

    @Override
    public void reset() {

    }

    @Override
    public void getLiveInfoList(@NonNull GetLiveInfoCallback callback) {
        if (sMockLocalData.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onLiveInfoLoaded(sMockLocalData);
        }
    }

    @Override
    public LiveInfo getLiveInfo(String rid) {
        for (LiveInfo sMockLocalDatum : sMockLocalData) {
            if (sMockLocalDatum.getTag().equals(rid)) {
                return sMockLocalDatum;
            }
        }
        return null;
    }

    @Override
    public void saveLiveInfo(LiveInfo liveInfo) {
        sMockLocalData.add(liveInfo);
    }

    @Override
    public void deleteAllLiveInfo() {
        sMockLocalData.clear();
    }

    public static LiveInfoLocalDataSource getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final LiveInfoLocalDataSource INSTANCE = new LiveInfoLocalDataSource();
    }

}

package com.sunlive.data.source;

import androidx.annotation.NonNull;

import com.sunlive.data.LiveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hengwei on 2020/6/15.
 */
public class LiveInfoRepository implements LiveInfoDataSource {

    private static LiveInfoRepository INSTANCE;

    private LiveInfoDataSource mRemoteDataSource;

    private LiveInfoDataSource mLocalDataSource;

    /**
     * 缓存数据
     */
    List<LiveInfo> mCachedData;
    /**
     * 缓存数据是否是脏数据，是的话需要请求网络重新获取数据，否则可以直接使用缓存数据
     */
    boolean mCacheDirty = false;

    private LiveInfoRepository(LiveInfoDataSource remoteDataSource,
                               LiveInfoDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
        mCachedData = new ArrayList<>();
    }

    @Override
    public void reset() {
        mCacheDirty = true;
    }

    @Override
    public void getLiveInfoList(@NonNull final GetLiveInfoCallback callback) {
        if (!mCacheDirty && mCachedData != null) {
            callback.onLiveInfoLoaded(mCachedData);
            return;
        }

        if (mCacheDirty) {
            getDataFromRemote(callback);
        } else {
            mLocalDataSource.getLiveInfoList(new GetLiveInfoCallback() {
                @Override
                public void onLiveInfoLoaded(List<LiveInfo> dataList) {
                    cacheData(dataList);
                    callback.onLiveInfoLoaded(dataList);
                }

                @Override
                public void onDataNotAvailable() {
                    getDataFromRemote(callback);
                }
            });
        }

    }

    @Override
    public LiveInfo getLiveInfo(String rid) {
        if (mCachedData == null || mCachedData.isEmpty()) {
            return null;
        }
        for (LiveInfo mCachedDatum : mCachedData) {
            if (mCachedDatum.getTag().equals(rid)) {
                return mCachedDatum;
            }
        }
        return null;
    }

    @Override
    public void saveLiveInfo(LiveInfo liveInfo) {
        mLocalDataSource.saveLiveInfo(liveInfo);
        mRemoteDataSource.saveLiveInfo(liveInfo);
        mCachedData.add(liveInfo);
    }

    @Override
    public void deleteAllLiveInfo() {
        mLocalDataSource.deleteAllLiveInfo();
        mRemoteDataSource.deleteAllLiveInfo();
        if (mCachedData == null) {
            mCachedData = new ArrayList<>();
        }
        mCachedData.clear();
    }

    private void getDataFromRemote(final GetLiveInfoCallback callback) {
        mRemoteDataSource.getLiveInfoList(new GetLiveInfoCallback() {
            @Override
            public void onLiveInfoLoaded(List<LiveInfo> dataList) {
                cacheData(dataList);
                refreshLocalDataSource(dataList);
                callback.onLiveInfoLoaded(dataList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<LiveInfo> dataList) {
        mLocalDataSource.deleteAllLiveInfo();
        for (LiveInfo liveInfo : dataList) {
            mLocalDataSource.saveLiveInfo(liveInfo);
        }
    }

    private void cacheData(List<LiveInfo> dataList) {
        mCachedData.clear();
        mCachedData.addAll(dataList);
    }

    public static LiveInfoRepository getInstance(LiveInfoDataSource remoteDataSource,
                                                 LiveInfoDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LiveInfoRepository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }


}

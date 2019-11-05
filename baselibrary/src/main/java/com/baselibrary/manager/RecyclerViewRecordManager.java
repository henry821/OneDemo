package com.baselibrary.manager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/**
 * Description 记录RecyclerView内部关键信息的管理类
 * Author wanghengwei
 * Date   2019/10/31 19:24
 */
public class RecyclerViewRecordManager {

    private List<RecyclerView.ViewHolder> mAttachedScrap;
    private List<RecyclerView.ViewHolder> mCachedViews;

    private RecyclerViewRecordManager() {

    }

    public static RecyclerViewRecordManager getInstance() {
        return ManagerHolder.INSTANCE;
    }

    public List<RecyclerView.ViewHolder> getAttachedScrap() {
        return mAttachedScrap;
    }

    public void setAttachedScrap(List<RecyclerView.ViewHolder> mAttachedScrap) {
        this.mAttachedScrap = mAttachedScrap;
        Log.e("_aa", "setAttachedScrap: " + mAttachedScrap.size());
    }

    public List<RecyclerView.ViewHolder> getCachedViews() {
        return mCachedViews;
    }

    public void setCachedViews(List<RecyclerView.ViewHolder> mCachedViews) {
        this.mCachedViews = mCachedViews;
        Log.e("_aa", "setCachedViews: " + mCachedViews.size());
    }

    private static final class ManagerHolder {
        private static final RecyclerViewRecordManager INSTANCE = new RecyclerViewRecordManager();
    }
}

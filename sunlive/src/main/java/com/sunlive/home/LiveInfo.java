package com.sunlive.home;

import java.util.List;

public class LiveInfo {

    private int page;
    private List<LiveInfoDetail> dataList;

    public LiveInfo(int page, List<LiveInfoDetail> dataList) {
        this.page = page;
        this.dataList = dataList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<LiveInfoDetail> getDataList() {
        return dataList;
    }

    public void setDataList(List<LiveInfoDetail> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "{" +
                "\"page\":" + page +
                ", \"dataList\":" + dataList +
                '}';
    }

    public static class LiveInfoDetail {
        private String cover;
        private String title;

        public LiveInfoDetail(String cover, String title) {
            this.cover = cover;
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"cover\":\"" + cover + "\"" +
                    ", \"title\":\"" + title + "\"" +
                    '}';
        }
    }
}

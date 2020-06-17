package com.sunlive.data;

import org.jetbrains.annotations.NotNull;

public class LiveInfo {

    private String rid;
    private String cover;
    private String title;
    private String tag;

    public LiveInfo(String rid, String cover, String title) {
        this.rid = rid;
        this.cover = cover;
        this.title = title;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @NotNull
    @Override
    public String toString() {
        return "{" +
                "\"rid\":\"" + rid + "\"" +
                ", \"cover\":\"" + cover + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"tag\":\"" + tag + "\"" +
                '}';
    }
}

package com.demo.beans;

/**
 * Description 首页列表Title
 * <br>Author wanghengwei
 * <br>Date   2018/12/4 18:07
 */
public class TitleBean {
    /**
     * 显示的Title
     */
    private String title;
    /**
     * 要跳转到的类
     */
    private Class toPkgClazz;

    public TitleBean(String title, Class toPkgClazz) {
        this.title = title;
        this.toPkgClazz = toPkgClazz;
    }

    public Class getToPkgClazz() {
        return toPkgClazz;
    }

    public String getTitle() {
        return title;
    }
}

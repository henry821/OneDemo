package com.baselibrary.beans;

/**
 * Description 转盘条目
 * Author wanghengwei
 * Date   2019/8/28 14:04
 */
public class TurnPlateViewItemBean {

    private String color;
    private String desc;

    public TurnPlateViewItemBean(String color, String desc) {
        this.color = color;
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

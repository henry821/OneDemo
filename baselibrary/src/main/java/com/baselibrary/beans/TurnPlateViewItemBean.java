package com.baselibrary.beans;

/**
 * Description 转盘条目
 * Author wanghengwei
 * Date   2019/8/28 14:04
 */
public class TurnPlateViewItemBean {

    private int id;
    private String color;
    private String desc;

    private float startAngle;
    private float endAngle;

    public TurnPlateViewItemBean(int id, String color, String desc) {
        this.id = id;
        this.color = color;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }
}

package com.demo.beans;


import android.util.Log;

/**
 * Description 转盘条目
 * Author wanghengwei
 * Date   2019/8/28 14:04
 */
public class TurnPlateViewItemBean {

    private static final String TAG = "TurnPlateViewItemBean";

    private String id;
    private String title;
    private String angle;
    private String color;

    private float startAngle;
    private float endAngle;

    public TurnPlateViewItemBean(String id, String title, String angle, String color) {
        this.id = id;
        this.title = title;
        this.angle = angle;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    /**
     * 获取当前条目扫过的角度，返回float类型
     *
     * @return 扫过的角度，float类型
     */
    public float getFloatAngle() {
        float result = 0;
        try {
            result = Float.parseFloat(angle);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    @Override
    public String toString() {
        return "TurnPlateViewItemBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", angle='" + angle + '\'' +
                ", color='" + color + '\'' +
                ", startAngle=" + startAngle +
                ", endAngle=" + endAngle +
                '}';
    }
}

package com.demo.autoplay;

/**
 * 自动播放的Item协议。
 * 通过计算{@link #getDetectedView()}的可见性，调用Item的激活或反激活方法
 * Created by kaige1 on 2018/11/14.
 */
public interface DetectableItem extends DetectableItemView {

    /**
     * 激活
     */
    void activate();

    /**
     * 反激活
     */
    void deactivate();
}

package com.demo.autoplay;

import android.view.ViewGroup;

/**
 * 在一个{@link ViewGroup}中，按照一定的规则进行探测，自动激活Item
 *
 * Created by kaige1 on 27/07/2017.
 */
public interface IDetectorImpl {

    /**
     * 计算所有的item，并按照规则进行Item的反激活和激活
     * @param viewGroup
     */
    void detect(ViewGroup viewGroup);

    /**
     * 停止检测
     */
    void stop();
}

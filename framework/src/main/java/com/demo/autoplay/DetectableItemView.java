package com.demo.autoplay;

import android.view.View;

/**
 * 可被自动播放框架检测的item协议
 * Created by kaige1 on 2018/11/14.
 */
public interface DetectableItemView {

    /**
     * @return 用于计算的View
     */
    View getDetectedView();
}

package com.demo.autoplay;

import android.widget.AbsListView;

/**
 * 对ListView适配自动播放时，由于ListView只有一个setOnScrollListener，
 * 而各个业务方在实现ListView的滑动事件分发时，方法五花八门，
 * 因此增加一个对ListView的描述，表示可以分发滑动事件，自动播放对此接口进行适配。
 * 同时，我们给出了一个通用的实现，见{@link ScrollDispatchListView}，业务方可以直接使用或参考实现
 *
 * Created by kaige1 on 2018/5/4.
 */
public interface ScrollDispatched {

    /**
     * 拓展原生的ListView.setOnScrollListener，支持添加Listener
     * @param listener
     */
    void addOnScrollListener(AbsListView.OnScrollListener listener);

    /**
     * 移除之前添加的listener
     * @param listener
     */
    void removeOnScrollListener(AbsListView.OnScrollListener listener);
}

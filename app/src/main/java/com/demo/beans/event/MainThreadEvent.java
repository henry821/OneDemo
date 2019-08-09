package com.demo.beans.event;

/**
 * Description EventBus主线程事件
 * Author wanghengwei
 * Date   2019/8/8 17:07
 */
public class MainThreadEvent {

    private String msg;

    public MainThreadEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.demo.beans.event;

/**
 * Description EventBus子线程事件
 * Author wanghengwei
 * Date   2019/8/8 17:07
 */
public class SubThreadEvent {

    private String msg;

    public SubThreadEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

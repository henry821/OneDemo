package com.demo.beans.reflection;

import com.demo.one.base.utils.LogUtil;

/**
 * Description 反射类父类
 * Author wanghengwei
 * Date   2019/6/13 16:19
 */
public class ReflectionParentBean {

    private String name;
    public String comment;

    public ReflectionParentBean() {
        LogUtil.w("ReflectionParentBean无参构造方法");
    }

    public ReflectionParentBean(String name) {
        LogUtil.w("ReflectionParentBean有参构造方法");
        this.name = name;
    }
}

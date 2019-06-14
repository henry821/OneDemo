package com.demo.beans.reflection;

import com.baselibrary.utils.LogUtil;

/**
 * Description 反射Demo类
 * Author wanghengwei
 * Date   2019/6/13 15:51
 */
public class ReflectionBean extends ReflectionParentBean {

    private int num;
    public static final int age = 1;

    public ReflectionBean() {
        LogUtil.w("ReflectionBean无参构造方法");
    }

    public ReflectionBean(int num) {
        LogUtil.w("ReflectionBean有参构造方法");
        this.num = num;
    }

    private void normalMethod1() {
        LogUtil.w("私有类型无参方法 normalMethod1");
        LogUtil.e("=====================================");
    }

    public void normalMethod2() {
        LogUtil.w("公有类型无参方法 normalMethod2");
        LogUtil.e("=====================================");
    }

    private String oneParamMethod1(String string) {
        LogUtil.w("私有类型、一个参数方法 oneParamMethod1 ---> " + string);
        LogUtil.e("=====================================");
        return "oneParamMethod1返回值 ---> " + string;
    }

    public void oneParamMethod2(String string) {
        LogUtil.w("公有类型、一个参数方法 oneParamMethod2 ---> " + string);
        LogUtil.e("=====================================");
    }
}

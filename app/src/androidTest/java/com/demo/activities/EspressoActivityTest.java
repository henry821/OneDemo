package com.demo.activities;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.demo.one.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Description {@link EspressoActivity}的测试类
 * Author wanghengwei
 * Date   2019/8/16 14:06
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoActivityTest {

    @Rule
    public ActivityTestRule<EspressoActivity> rule = new ActivityTestRule<>(EspressoActivity.class);

    /**
     * 测试按钮点击,验证TextView显示是否正确
     */
    @Test
    public void clickTest() {
        //点击按钮
        onView(withId(R.id.btn_test)).perform(click());
        //验证TextView显示是否正确
        onView(withId(R.id.tv_result)).check(matches(withText("测试点击事件成功")));
    }

    /**
     * 测试登录功能,验证TextView显示是否正确
     */
    @Test
    public void loginTest() {
        //输入用户名
        onView(withId(R.id.et_username))
                .perform(clearText()
                        , replaceText("test111")
                        , closeSoftKeyboard())
                .check(matches(withText("test111")));
        //输入密码
        onView(withId(R.id.et_password))
                .perform(clearText()
                        , replaceText("123456")
                        , closeSoftKeyboard())
                .check(matches(withText("123456")));
        //点击登录按钮
        onView(withId(R.id.btn_login))
                .perform(click());
        //验证输出是否正确
        onView(withId(R.id.tv_result))
                .check(matches(withText("登录成功")));
    }

}

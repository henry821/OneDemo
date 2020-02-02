package com.demo.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.beans.MemoryLeakSingleton;
import com.demo.one.R;
import com.demo.one.base.utils.LogUtil;

/**
 * 通过LeakCanary分析内存泄漏路径
 * 介绍了四种内存泄漏的情况
 * 1.单例类持有Activity引用
 *     原因：单例生命周期与应用程序相同，所以持有Activity引用后导致Activity即使退出也无法被回收
 * 2.非静态内部类的静态实例
 *     原因：非静态内部类持有外部类引用，同时静态实例生命周期与应用程序相同，所以导致Activity即使退出也无法被回收
 * 3.非静态Thread子类 或者 Thread的匿名内部类
 *     原因：非静态Thread子类或者Thread的匿名内部类都会持有外部类引用，当执行耗时操作时导致Activity无法被回收
 * 4.非静态Handler子类 或者 Handler匿名内部类 或者 Handler.post(new Runnable)
 *     原因：前两种由于Handler持有外部类引用，最后一种由于实现了Runnable的匿名内部类，所以Runnable持有外部类引用
 *   所以执行耗时操作或者存在大量未处理Message时导致Activity无法被回收
 */
public class LeakCanaryActivity extends AppCompatActivity {

    private static InnerClass sInnerClass;
    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                LogUtil.e("handler打印消息");
            }
        };

        Button btnSingleton = findViewById(R.id.btn_singleton);
        Button btnNormalInnerClassStaticInstance = findViewById(R.id.btn_normal_inner_class_static_instance);
        Button btnInnerThread = findViewById(R.id.btn_inner_thread);
        Button btnNormalHandler = findViewById(R.id.btn_normal_handler);

        btnSingleton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryLeakSingleton.getInstance(LeakCanaryActivity.this);
            }
        });

        btnNormalInnerClassStaticInstance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sInnerClass = new InnerClass();
            }
        });

        btnInnerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            LogUtil.e("执行了多线程");
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        btnNormalHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendMessage(Message.obtain());
            }
        });
    }

    /**
     * 非静态内部类
     */
    private class InnerClass {

    }
}

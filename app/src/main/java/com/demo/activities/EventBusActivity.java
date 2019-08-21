package com.demo.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baselibrary.utils.LogUtil;
import com.demo.beans.event.MainThreadEvent;
import com.demo.beans.event.SubThreadEvent;
import com.demo.one.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Description EventBus示例Activity
 * Author wanghengwei
 * Date   2019/8/8 16:57
 */
public class EventBusActivity extends AppCompatActivity {

    private TextView tvEvent1;
    private TextView tvEvent2;

    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_bus);

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog == null) {
                    createDialog();
                }
                mDialog.show();
            }
        });
        tvEvent1 = findViewById(R.id.tv_event_1);
        tvEvent2 = findViewById(R.id.tv_event_2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainThreadEvent event) {
        LogUtil.e(event.getMsg());
        tvEvent1.setText(event.getMsg());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(SubThreadEvent event) {
        LogUtil.e(event.getMsg());
        tvEvent2.setText(event.getMsg());
    }

    private void createDialog() {
        mDialog = new AlertDialog.Builder(this)
                .setMessage("发送Event")
                .setPositiveButton("在主线程发送事件", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new MainThreadEvent(Thread.currentThread().getName()));
                    }
                })
                .setNegativeButton("在子线程发送事件", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new SubThreadEvent(Thread.currentThread().getName()));
                            }
                        }.start();
                    }
                })
                .create();
    }
}

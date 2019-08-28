package com.demo.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baselibrary.utils.LogUtil;
import com.demo.one.R;
import com.demo.service.BindModeService;
import com.demo.service.StartModeService;

public class ServiceActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnBind;

    private ServiceConnection mConnection;
    private BindModeService.MyBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btnStart = findViewById(R.id.btn_start);
        btnBind = findViewById(R.id.btn_bind);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.e(getClass().getName() + "与" + name.getClass().getName() + "建立了连接");
                mBinder = (BindModeService.MyBinder) service;
                mBinder.execute();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.e(getClass().getName() + "与" + name.getClass().getName() + "断开了连接");
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ServiceActivity.this, StartModeService.class));
            }
        });

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(ServiceActivity.this, BindModeService.class)
                        , mConnection, BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(ServiceActivity.this, StartModeService.class));
        unbindService(mConnection);
        super.onDestroy();

    }
}

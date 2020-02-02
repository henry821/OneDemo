package com.demo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demo.one.R;
import com.demo.one.base.utils.LogUtil;

import java.lang.ref.WeakReference;

public class BlockCanaryActivity extends AppCompatActivity {

    private WeakHandler mHandler;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_canary);

        mHandler = new WeakHandler(this);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(Color.GREEN);
                mHandler.sendEmptyMessage(0);
            }
        });

    }

    private static class WeakHandler extends Handler {
        private WeakReference<BlockCanaryActivity> mRef;

        WeakHandler(BlockCanaryActivity activity) {
            mRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                LogUtil.e("开始卡顿5秒");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mRef.get().mButton.setBackgroundColor(Color.RED);
            }
        }
    }

}

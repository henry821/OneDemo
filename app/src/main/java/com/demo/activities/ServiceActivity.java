package com.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demo.one.R;
import com.demo.service.StartModeService;

public class ServiceActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        btnStart = findViewById(R.id.btn_start);
        btnBind = findViewById(R.id.btn_bind);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ServiceActivity.this, StartModeService.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(ServiceActivity.this, StartModeService.class));
        super.onDestroy();

    }
}

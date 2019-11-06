package com.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.one.R;

public class JniActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(stringFromJNI());

    }
}

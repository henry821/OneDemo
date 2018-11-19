package com.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.one.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitleList();

    }

    private void initTitleList() {
    }
}

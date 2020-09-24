package com.demo.autoplayarch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.autoplay.AutoPlayManager;
import com.demo.one.R;

public class AutoPlayArchActivity extends AppCompatActivity {

    private RecyclerView mRvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_play_arch);

        AutoPlayManager.init(this);

        mRvMain = findViewById(R.id.rv_main);

        mRvMain.setLayoutManager(new LinearLayoutManager(this));
        mRvMain.setAdapter(new AutoPlayArchAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AutoPlayManager.bind(mRvMain).detect();
    }
}
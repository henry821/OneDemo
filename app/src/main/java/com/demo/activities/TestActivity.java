package com.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.one.R;
import com.demo.widgets.TurnPlateView;

/**
 * Description 测试Activity，用于临时试验
 * Author wanghengwei
 * Date   2019/6/26 17:44
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final TurnPlateView turnPlatView = findViewById(R.id.turnPlatView);
        turnPlatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnPlatView.startAnim();
            }
        });


    }
}

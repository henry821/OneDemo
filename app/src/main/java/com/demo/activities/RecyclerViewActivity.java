package com.demo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.adapters.RecyclerViewLearningAdapter;
import com.demo.one.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    public static final int ITEM_CHANGED_COUNT = 10;

    private RecyclerViewLearningAdapter mAdapter;

    private int mAddTimes;
    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        resetData();

        Button btnReset = findViewById(R.id.btn_reset);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDel = findViewById(R.id.btn_del);
        RecyclerView rvMain = findViewById(R.id.rv_main);

        mAdapter = new RecyclerViewLearningAdapter(this, mDataList);

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(mAdapter);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
                mAdapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                mAdapter.notifyItemRangeInserted(mDataList.size() - ITEM_CHANGED_COUNT
                        , ITEM_CHANGED_COUNT);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delData();
                mAdapter.notifyItemRangeRemoved(mDataList.size(), ITEM_CHANGED_COUNT);
            }
        });
    }

    private void resetData() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        for (int i = 0; i < ITEM_CHANGED_COUNT; i++) {
            mDataList.add("初始数据: " + i);
        }
    }

    private void addData() {
        mAddTimes++;
        for (int i = 0; i < ITEM_CHANGED_COUNT; i++) {
            mDataList.add("新增数据" + mAddTimes + ": " + i);
        }
    }

    private void delData() {
        if (mDataList.size() < ITEM_CHANGED_COUNT) {
            return;
        }
        for (int i = 0; i < ITEM_CHANGED_COUNT; i++) {
            mDataList.remove(mDataList.size() - 1);
        }
    }
}

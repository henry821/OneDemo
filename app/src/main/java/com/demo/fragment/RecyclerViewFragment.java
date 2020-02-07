package com.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.adapters.RecyclerViewLearningAdapter;
import com.demo.one.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends BaseFragment {

    public static final int ITEM_CHANGED_COUNT = 10;

    private RecyclerViewLearningAdapter mAdapter;

    private int mAddTimes;
    private List<String> mDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        Button btnReset = root.findViewById(R.id.btn_reset);
        Button btnAdd = root.findViewById(R.id.btn_add);
        Button btnDel = root.findViewById(R.id.btn_del);
        RecyclerView rvMain = root.findViewById(R.id.rv_main);

        mAdapter = new RecyclerViewLearningAdapter(getContext(), mDataList);

        rvMain.setLayoutManager(new LinearLayoutManager(getContext()));
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

        return root;
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

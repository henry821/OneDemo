package com.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.adapters.TitleListNormalAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 热更新示例Activity
 * <br>Author wanghengwei
 * <br>Date   2019/6/13 14:52
 */
public class HotFixActivity extends AppCompatActivity {

    private List<TitleBean> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);

        initTitleList();

        RecyclerView rvMain = findViewById(R.id.rv_main);
        TitleListNormalAdapter mAdapter = new TitleListNormalAdapter(this, mTitleList, new TitleListNormalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TitleBean bean) {
                switch (position) {
                    case 1:
                        ReflectionUtil.printReflectionDemoInfo();
                        break;
                    default:
                        break;
                }
            }
        });

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(mAdapter);
    }

    private void initTitleList() {
        mTitleList = new ArrayList<>();
        mTitleList.add(new TitleBean("类的加载机制", null));
        mTitleList.add(new TitleBean("反射", null));
    }

}

package com.demo.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.adapter.TitleListAdapter;
import com.demo.one.R;
import com.demo.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<String> mTitleList;

    private ListView lvTitle;
    private TitleListAdapter adapter;

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    void initData() {
        initTitleList();
    }

    @Override
    void initView() {
        lvTitle = findViewById(R.id.lv_title);
        adapter = new TitleListAdapter(this, mTitleList);
        lvTitle.setAdapter(adapter);
        lvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        IntentUtil.gotoActivity(mContext,SurfaceViewActivity.class,null);
                        break;
                }
            }
        });
    }

    private void initTitleList() {
        mTitleList = new ArrayList<>();
        mTitleList.add("SurfaceView显示图片");
    }
}

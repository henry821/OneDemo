package com.demo.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.demo.adapters.TitleListNormalAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.utils.CreateBugUtil;
import com.demo.utils.HotFix;
import com.demo.utils.ReflectUtils;

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
                        ReflectUtils.printReflectionDemoInfo();
                        break;
                    case 2:
                        CreateBugUtil.createBug(HotFixActivity.this);
                        break;
                    case 3:
                        checkFix();
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
        mTitleList.add(new TitleBean("测试热修复-->查看当前效果", null));
        mTitleList.add(new TitleBean("测试热修复-->打入补丁包", null));
    }

    private void checkFix() {
        try {
            String dexPath = Environment.getExternalStorageDirectory() + "/fixed.dex";
            HotFix.fix(this, dexPath);

            Toast.makeText(HotFixActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(HotFixActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}

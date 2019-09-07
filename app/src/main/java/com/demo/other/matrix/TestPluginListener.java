package com.demo.other.matrix;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.demo.activities.IssuesListActivity;
import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

import java.lang.ref.SoftReference;

/**
 * Description 接收matrix处理后的数据
 * Author wanghengwei
 * Date   2019/9/7 14:50
 */
public class TestPluginListener extends DefaultPluginListener {

    public static final String TAG = "TestPluginListener";

    public SoftReference<Context> softReference;

    public TestPluginListener(Context context) {
        super(context);
        softReference = new SoftReference<>(context);
    }

    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());

        IssuesMap.put(IssueFilter.getCurrentFilter(), issue);
        jumpToIssueActivity();
    }

    public void jumpToIssueActivity() {
        Context context = softReference.get();
        Intent intent = new Intent(context, IssuesListActivity.class);

        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);

    }

}
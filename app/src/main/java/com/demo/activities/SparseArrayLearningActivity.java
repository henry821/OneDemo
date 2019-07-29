package com.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baselibrary.utils.LogUtil;
import com.baselibrary.utils.ReflectionUtil;
import com.demo.one.R;
import com.sourcecode.sparsearray.MySparseArray;

import java.lang.reflect.Array;

public class SparseArrayLearningActivity extends AppCompatActivity {

    private MySparseArray<String> mySparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparse_array_learning);

        mySparseArray = new MySparseArray<>();
        printArrayData();

        mySparseArray.put(11,"第一个元素");
        printArrayData();

        mySparseArray.put(17,"第二个元素");
        printArrayData();
    }

    private void printArrayData() {
        StringBuilder builder = new StringBuilder();
        try {
            Object mKeys = ReflectionUtil.getField(mySparseArray, mySparseArray.getClass(), "mKeys");
            int length = Array.getLength(mKeys);
            for (int i = 0; i < length; i++) {
                int key = mySparseArray.keyAt(i);
                String value = mySparseArray.valueAt(i);
                builder.append("[").append(key).append(",").
                        append(value == null ? "null" : value).append("]");
            }
            LogUtil.e("当前数据：数组长度：" + length + ", 实际长度：" + mySparseArray.size());
            LogUtil.e("当前数据：" + builder.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package com.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.one.R;
import com.demo.one.base.utils.LogUtil;
import com.demo.utils.ReflectUtils;
import com.sourcecode.sparsearray.MySparseArray;

import java.lang.reflect.Array;

public class SparseArrayFragment extends BaseFragment {

    private MySparseArray<String> mySparseArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySparseArray = new MySparseArray<>();
        printArrayData();

        mySparseArray.put(11, "第一个元素");
        printArrayData();

        mySparseArray.put(17, "第二个元素");
        printArrayData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sparse_array, container, false);

    }

    private void printArrayData() {
        StringBuilder builder = new StringBuilder();
        try {
            Object mKeys = ReflectUtils.readField(mySparseArray, "mKeys");
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

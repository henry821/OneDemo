package com.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.one.R;

public class JniFragment extends BaseFragment {

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jni, container, false);

        TextView tvContent = root.findViewById(R.id.tv_content);
        tvContent.setText(stringFromJNI());

        return root;
    }
}

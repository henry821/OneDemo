package com.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.autoplayarch.AutoPlayArchActivity;
import com.demo.one.R;

/**
 * Description 自动检测列表可见条目Fragment
 * Author wanghengwei
 * Date   2020/9/24 14:27
 */
public class AutoPlayFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auto_play, container, false);
        Button btn = root.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AutoPlayArchActivity.class));
            }
        });
        return root;
    }
}

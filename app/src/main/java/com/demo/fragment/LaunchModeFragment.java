package com.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.activity.launchmode.SingleInstanceActivity;
import com.demo.activity.launchmode.SingleTaskActivity;
import com.demo.activity.launchmode.SingleTopActivity;
import com.demo.activity.launchmode.StandardActivity;
import com.demo.one.R;

/**
 * Description 验证LaunchMode
 * Author wanghengwei
 * Date   2020/4/20
 */
public class LaunchModeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_launch_mode, container, false);

        root.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StandardActivity.class));
            }
        });
        root.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SingleTopActivity.class));
            }
        });
        root.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SingleTaskActivity.class));
            }
        });
        root.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SingleInstanceActivity.class));
            }
        });

        return root;

    }
}

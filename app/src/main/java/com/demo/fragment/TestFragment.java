package com.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.one.R;
import com.demo.widgets.TurnPlateView;

/**
 * Description 测试Activity，用于临时试验
 * Author wanghengwei
 * Date   2019/6/26 17:44
 */
public class TestFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        TurnPlateView turnPlatView = root.findViewById(R.id.turnPlatView);
        turnPlatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TurnPlateView) v).start(3);
            }
        });
        return root;
    }
}

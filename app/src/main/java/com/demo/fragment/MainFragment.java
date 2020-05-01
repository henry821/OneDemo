package com.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.one.R;
import com.demo.viewmodel.TitleViewModel;

/**
 * Description
 * Author wanghengwei
 * Date   2020/4/30
 */
public class MainFragment extends BaseFragment {

    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        drawerLayout = root.findViewById(R.id.drawer_layout);
        TextView tvTitle = root.findViewById(R.id.tv_title);
        RecyclerView rvNav = root.findViewById(R.id.rv_nav);


        TitleViewModel mTaskViewModel = new ViewModelProvider(this).get(TitleViewModel.class);

        TitleListDataBindingAdapter adapter = new TitleListDataBindingAdapter(getContext(),
                mTaskViewModel.getTitles().getValue());

        rvNav.setAdapter(adapter);
        rvNav.setLayoutManager(new LinearLayoutManager(getContext()));

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        return root;
    }
}

package com.demo.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ActivityMainBinding;
import com.demo.utils.IntentUtil;
import com.demo.viewmodel.TitleViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TitleViewModel mTaskViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mTaskViewModel = ViewModelProviders.of(this).get(TitleViewModel.class);

        TitleListDataBindingAdapter adapter = new TitleListDataBindingAdapter(this,
                mTaskViewModel.getTitles().getValue(),
                new TitleListDataBindingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TitleBean bean) {
                        IntentUtil.gotoActivity(MainActivity.this, bean.getToPkgClazz(), null);
                    }
                });

        binding.setCallback(this);
        binding.setAdapter(adapter);
        binding.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

}

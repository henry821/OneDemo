package com.demo.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.beans.TitleBean;
import com.demo.one.R;
import com.demo.one.databinding.ActivityMainBinding;
import com.demo.viewmodel.TitleViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TitleViewModel mTaskViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();

        mTaskViewModel = ViewModelProviders.of(this).get(TitleViewModel.class);

        TitleListDataBindingAdapter adapter = new TitleListDataBindingAdapter(this,
                mTaskViewModel.getTitles().getValue(),
                new TitleListDataBindingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TitleBean bean) {
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        if (bean.getToPkgClazz().getSimpleName().equals("ServiceFragment")) {
                            ServiceFragment fragment = new ServiceFragment();
                            getLifecycle().addObserver(fragment);
                            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                            transaction.add(R.id.fl_content, fragment).commitAllowingStateLoss();
                        }
                    }
                });

        binding.setCallback(this);
        binding.setAdapter(adapter);
        binding.setLayoutManager(new LinearLayoutManager(this));

    }

    public void openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

}

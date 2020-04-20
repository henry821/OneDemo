package com.demo.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.adapters.TitleListDataBindingAdapter;
import com.demo.beans.TitleBean;
import com.demo.fragment.JniFragment;
import com.demo.fragment.LaunchModeFragment;
import com.demo.fragment.LeakCanaryFragment;
import com.demo.fragment.RecyclerViewFragment;
import com.demo.fragment.RetrofitFragment;
import com.demo.fragment.ServiceFragment;
import com.demo.fragment.SparseArrayFragment;
import com.demo.fragment.TestFragment;
import com.demo.one.R;
import com.demo.one.databinding.ActivityMainBinding;
import com.demo.viewmodel.TitleViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();

        TitleViewModel mTaskViewModel = ViewModelProvider.
                AndroidViewModelFactory.getInstance(getApplication()).create(TitleViewModel.class);

        TitleListDataBindingAdapter adapter = new TitleListDataBindingAdapter(this,
                mTaskViewModel.getTitles().getValue(),
                new TitleListDataBindingAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(TitleBean bean) {
                        FragmentManager supportFragmentManager = getSupportFragmentManager();
                        String fragmentName = bean.getToPkgClazz().getSimpleName();
                        getLifecycle().removeObserver((LifecycleObserver) mCurrentFragment);
                        if (ServiceFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new ServiceFragment();
                        } else if (LaunchModeFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new LaunchModeFragment();
                        } else if (RetrofitFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new RetrofitFragment();
                        } else if (LeakCanaryFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new LeakCanaryFragment();
                        } else if (JniFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new JniFragment();
                        } else if (RecyclerViewFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new RecyclerViewFragment();
                        } else if (SparseArrayFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new SparseArrayFragment();
                        } else if (TestFragment.class.getSimpleName().equals(fragmentName)) {
                            mCurrentFragment = new TestFragment();
                        }
                        getLifecycle().addObserver((LifecycleObserver) mCurrentFragment);
                        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                        transaction.replace(R.id.fl_content, mCurrentFragment).commitAllowingStateLoss();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                    }
                });

        binding.setCallback(this);
        binding.setAdapter(adapter);
        binding.setLayoutManager(new LinearLayoutManager(this));

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
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

}

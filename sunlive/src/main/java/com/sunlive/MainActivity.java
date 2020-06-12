package com.sunlive;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.sunlive.home.HomeActivity;
import com.sunlive.profile.ProfileActivity;
import com.sunlive.video.VideoActivity;

public class MainActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(getLocalActivityManager());

        tabHost.addTab(tabHost.newTabSpec(HomeActivity.class.getSimpleName())
                .setIndicator(getString(R.string.home))
                .setContent(new Intent(this, HomeActivity.class)));
        tabHost.addTab(tabHost.newTabSpec(VideoActivity.class.getSimpleName())
                .setIndicator(getString(R.string.video))
                .setContent(new Intent(this, VideoActivity.class)));
        tabHost.addTab(tabHost.newTabSpec(ProfileActivity.class.getSimpleName())
                .setIndicator(getString(R.string.profile))
                .setContent(new Intent(this, ProfileActivity.class)));
    }
}

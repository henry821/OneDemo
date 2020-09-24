package com.demo.autoplayarch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.autoplay.DetectableItem;
import com.demo.one.R;

/**
 * Created by hengwei on 2020/9/18.
 */
public class AutoPlayItemView extends FrameLayout implements DetectableItem {

    private TextView tvPosition;
    private TextView tvContent;

    public AutoPlayItemView(@NonNull Context context) {
        this(context, null);
    }

    public AutoPlayItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.item_auto_play, this);

        tvPosition = findViewById(R.id.tv_position);
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    public void activate() {
        tvContent.setText("激活");
    }

    @Override
    public void deactivate() {
        tvContent.setText("未激活");
    }

    @Override
    public View getDetectedView() {
        return this;
    }

    public void setPosition(String content){
        tvPosition.setText(content);
    }
}

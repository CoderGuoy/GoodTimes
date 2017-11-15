package com.coder.guoy.goodtimes.ui;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityAnimatedBinding;

public class AnimatedActivity extends MvvmBaseActivity<ActivityAnimatedBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated);
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        bindingView.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable) bindingView.logo.getDrawable()).start();
            }
        });
    }
}

package com.coder.guoy.goodtimes.ui.navigation;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivitySettingBinding;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月27日
 * @Descrpiton:设置
 */
public class SettingActivity extends MvvmBaseActivity<ActivitySettingBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bindingView.button.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

    @Override
    public void onClick(View v) {
        Animator animator = ViewAnimationUtils.createCircularReveal(bindingView.image,
                0,0,0,100000);
        animator.setDuration(5000);
        animator.start();
    }
}

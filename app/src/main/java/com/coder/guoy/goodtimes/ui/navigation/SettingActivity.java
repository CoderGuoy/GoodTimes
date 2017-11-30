package com.coder.guoy.goodtimes.ui.navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        Log.i("wid", "" + bindingView.image.getWidth());
        ObjectAnimator animator = ObjectAnimator.ofFloat(bindingView.image,
                "translationX", (bindingView.image.getWidth()), 0);
        animator.setDuration(2000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                bindingView.image.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }
}

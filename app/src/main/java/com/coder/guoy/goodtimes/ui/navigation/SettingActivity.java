package com.coder.guoy.goodtimes.ui.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivitySettingBinding;
import com.coder.guoy.goodtimes.utils.StatusBarUtils;

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
        initView();
    }

    private void initView() {
        bindingView.textTitle.setText(getIntent().getStringExtra("title"));
        bindingView.toolbar.setBackgroundColor(getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)));
        StatusBarUtils.setColor(this, getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)), 0);
        bindingView.back.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}

package com.coder.guoy.goodtimes.ui.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityAboutMeBinding;
import com.coder.guoy.goodtimes.utils.StatusBarUtils;
import com.coder.guoy.goodtimes.utils.SystemUtil;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月27日
 * @Descrpiton:关于我们
 */
public class AboutMeActivity extends MvvmBaseActivity<ActivityAboutMeBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        StatusBarUtils.setColor(this, Color.rgb(229, 67, 124),
                0);
        initView();
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

    private void initView() {
        bindingView.textTitle.setText(getIntent().getStringExtra("title"));
        bindingView.toolbar.setBackgroundColor(getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)));
        StatusBarUtils.setColor(this, getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)), 0);
        bindingView.textVerson.setText("版本号" + SystemUtil.getAppVersionName(getApplicationContext()));
        bindingView.back.setOnClickListener(this);
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

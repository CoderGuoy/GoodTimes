package com.coder.guoy.goodtimes.ui.navigation;

import android.graphics.Color;
import android.os.Bundle;

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
public class AboutMeActivity extends MvvmBaseActivity<ActivityAboutMeBinding> {

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
        bindingView.textTitle.setText("关于我们");
        bindingView.textVerson.setText("本版号" + SystemUtil.getAppVersionName(getApplicationContext()));
    }
}

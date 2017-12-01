package com.coder.guoy.goodtimes.ui.navigation;

import android.graphics.Color;
import android.os.Bundle;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityMoneyBinding;
import com.coder.guoy.goodtimes.utils.StatusBarUtils;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月27日
 * @Descrpiton:打赏
 */
public class MoneyActivity extends MvvmBaseActivity<ActivityMoneyBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        initView();
    }

    private void initView() {
        StatusBarUtils.setColor(this, getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)), 0);
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

}

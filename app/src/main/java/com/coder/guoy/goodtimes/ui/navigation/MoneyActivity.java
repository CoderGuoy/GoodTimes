package com.coder.guoy.goodtimes.ui.navigation;

import android.os.Bundle;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityMoneyBinding;
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
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

}

package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityImageDeatilBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

public class ImageDeatilActivity extends MvvmBaseActivity<ActivityImageDeatilBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_deatil);
    }

    @Override
    protected void getData() {
        super.getData();
        initView();
    }

    private void initView() {
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //设置图片
        GlideUtils.setImage(imageUrl, bindingView.image);
        showContentView();
    }
}

package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityImageDeatilBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:图片详情页
 */
public class ImageDeatilActivity extends MvvmBaseActivity<ActivityImageDeatilBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_deatil);
        initView();
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
    }

    private void initView() {
        ImageView image = findViewById(R.id.imageview_detail);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //设置图片
        GlideUtils.setDetailImage(imageUrl, image);
    }
}

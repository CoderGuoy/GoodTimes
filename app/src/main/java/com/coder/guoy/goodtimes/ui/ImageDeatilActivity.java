package com.coder.guoy.goodtimes.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.utils.GlideUtils;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:图片详情页
 */
public class ImageDeatilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_deatil);
        transparentStatusBar();
        initView();
    }

    /**
     * 透明状态栏
     */
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initView() {
        ImageView image = findViewById(R.id.imageview_detail);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //设置图片
        GlideUtils.setDetailImage(imageUrl, image);
    }
}

package com.coder.guoy.goodtimes.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        setLayoutAnimation();
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

    /**
     * 转场动画
     */
    private void setLayoutAnimation() {
        LinearLayout layout = findViewById(R.id.activity_image_deatil);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(300);
        LayoutAnimationController controller = new LayoutAnimationController(alpha, 0.3f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layout.setLayoutAnimation(controller);
    }

    private void initView() {
        ImageView image = findViewById(R.id.imageview_detail);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //设置图片
        GlideUtils.setDetailImage(imageUrl, image);
    }
}

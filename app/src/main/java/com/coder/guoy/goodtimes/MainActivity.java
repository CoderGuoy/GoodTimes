package com.coder.guoy.goodtimes;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;

import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        initDrawerlayout();
    }

    private void initView() {
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageHome.setImageResource(R.drawable.model03);
        getBitmapColor();
    }

    /**
     * 初始化侧拉菜单
     */
    private void initDrawerlayout() {
        View headerView = binding.navigationview.getHeaderView(0);
        NavigationHeaderBinding bind = DataBindingUtil.bind(headerView);
        bind.llNavVideo.setOnClickListener(listener);
        bind.llNav2.setOnClickListener(listener);
        bind.llNav3.setOnClickListener(listener);
        bind.llNav4.setOnClickListener(listener);
        bind.llNav5.setOnClickListener(listener);
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
     * Palette从图片(Bitmap)中提取颜色
     */
    private void getBitmapColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model03);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int color = 0;
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                if (lightVibrantSwatch != null) {
                    //获取有活力的亮色 颜色
                    color = lightVibrantSwatch.getRgb();
                } else {
                    //获取有活力的 颜色
                    color = vibrantSwatch.getRgb();
                }
                binding.collapsingtollbar.setContentScrimColor(color);
                binding.textModel1.setTextColor(color);
                binding.textModel1More.setBackgroundColor(color);
                binding.textModel2.setTextColor(color);
                binding.textModel2More.setBackgroundColor(color);
                binding.textModel3.setTextColor(color);
                binding.textModel3More.setBackgroundColor(color);
                binding.textModel4.setTextColor(color);
                binding.textModel4More.setBackgroundColor(color);
                binding.textModel5.setTextColor(color);
                binding.textModel5More.setBackgroundColor(color);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(final View v) {
            binding.drawerlayout.closeDrawer(GravityCompat.START);
            binding.drawerlayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()) {
                        case R.id.ll_nav_video:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_2:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_3:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_4:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_5:
                            ToastUtil.show("敬请期待");
                            break;
                    }
                }
            }, 260);
        }
    };
}
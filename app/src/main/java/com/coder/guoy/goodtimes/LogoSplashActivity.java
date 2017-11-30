package com.coder.guoy.goodtimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/11/30
 * @Descrpiton:启动加载页面，不加载布局
 * https://juejin.im/post/581f4ad667f3560058a33057
 * http://blog.lmj.wiki/2016/08/29/app-opti/app_opt_app_startup2/
 */
public class LogoSplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注意, 这里并没有setContentView, 单纯只是用来跳转到相应的Activity.
        // 目的是减少首屏渲染
        startActivity(new Intent(LogoSplashActivity.this,MainActivity.class));
        finish();
    }
}

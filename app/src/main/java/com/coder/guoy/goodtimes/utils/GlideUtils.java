package com.coder.guoy.goodtimes.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coder.guoy.goodtimes.App;
import com.coder.guoy.goodtimes.R;


/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:
 */
public class GlideUtils {
    public static void setImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext()).load(url)
                .error(R.drawable.loadingfaile)     //加载失败显示的图片
                .crossFade()                        //渐显动画
                .into(imageview);
    }
    public static void setDetailImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext()).load(url)
                .error(R.drawable.loadingfaile)     //加载失败显示的图片
                .into(imageview);
    }
}

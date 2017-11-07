package com.coder.guoy.goodtimes.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coder.guoy.goodtimes.App;
import com.coder.guoy.goodtimes.R;


/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:GlideV4
 */
public class GlideUtils {
    public static void setImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loadingfaile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.1f)
                .into(imageview);
    }

    public static void setDetailImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
                .error(R.drawable.loadingfaile)     //加载失败显示的图片
                .into(imageview);
    }
}

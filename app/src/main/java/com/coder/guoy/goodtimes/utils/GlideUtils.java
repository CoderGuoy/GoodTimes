package com.coder.guoy.goodtimes.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coder.guoy.goodtimes.App;


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
                .into(imageview);
    }

    public static void setDetailImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
                .into(imageview);
    }
}

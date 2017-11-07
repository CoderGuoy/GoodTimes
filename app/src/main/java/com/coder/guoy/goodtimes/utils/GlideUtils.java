package com.coder.guoy.goodtimes.utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.coder.guoy.goodtimes.App;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.progress.GlideImageView;
import com.coder.guoy.goodtimes.progress.OnGlideImageViewListener;


/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:GlideV4
 */
public class GlideUtils {
    public static void progressImage(String url, GlideImageView imageview) {
        RequestOptions options = imageview.requestOptions(R.color.placeholder_color)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        imageview.load(url, options).listener(new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                    ToastUtil.show(exception.getMessage());
                }
                Log.i("percent", percent + "");
            }
        });
    }

    public static void setImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.loadingfaile)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .thumbnail(0.1f)
                .into(imageview);
    }

    public static void setDetailImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
//                .error(R.drawable.loadingfaile)     //加载失败显示的图片
                .into(imageview);
    }
}

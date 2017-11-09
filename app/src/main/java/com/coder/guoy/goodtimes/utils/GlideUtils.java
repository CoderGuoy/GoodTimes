package com.coder.guoy.goodtimes.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.coder.guoy.goodtimes.App;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.progress.CircleProgressView;
import com.coder.guoy.goodtimes.progress.GlideImageLoader;
import com.coder.guoy.goodtimes.progress.GlideImageView;
import com.coder.guoy.goodtimes.progress.OnGlideImageViewListener;


/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:GlideV4
 */
public class GlideUtils {
    public static void progressImage(String url, GlideImageView imageview,
                                     final CircleProgressView progressView) {
        RequestOptions options = imageview.requestOptions(R.color.placeholder_color)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        // 第一种方式加载
//        imageview.load(url, options).listener(new OnGlideImageViewListener() {
//            @Override
//            public void onProgress(int percent, boolean isDone, GlideException exception) {
//                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
//                    ToastUtil.show(exception.getMessage());
//                }
//                progressView.setProgress(percent);
//                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
//            }
//        });

        // 第二种方式加载：可以解锁更多功能
        GlideImageLoader imageLoader = imageview.getImageLoader();
        imageLoader.setOnGlideImageViewListener(url, new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                    ToastUtil.show(exception.getMessage());
                }
                progressView.setProgress(percent);
                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });
        imageLoader.requestBuilder(url, options)
                .thumbnail(0.1f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageview);
    }

    public static void setImage(String url, ImageView imageview) {
        RequestOptions options = new RequestOptions().placeholder(R.color.placeholder_color)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
                .apply(options)
                .thumbnail(0.1f)
                .into(imageview);
    }

    public static void setDetailImage(String url, ImageView imageview) {
        Glide.with(App.getInstance().getApplicationContext())
                .load(url)
//                .error(R.drawable.loadingfaile)     //加载失败显示的图片
                .into(imageview);
    }
}

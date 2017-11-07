package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.progress.CircleProgressView;
import com.coder.guoy.goodtimes.progress.GlideImageLoader;
import com.coder.guoy.goodtimes.progress.GlideImageView;
import com.coder.guoy.goodtimes.progress.OnGlideImageViewListener;
import com.coder.guoy.goodtimes.utils.ToastUtil;

public class ProgressImageAcitivty extends AppCompatActivity {

    private CircleProgressView mProgressView;
    private GlideImageView glideImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_image);
        mProgressView = findViewById(R.id.circleprogressview);
        glideImageView = findViewById(R.id.glideimageview);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressImage("http://pic1.win4000.com/wallpaper/2017-11-06/59ffc2e077d11.jpg", glideImageView);
            }
        });
    }

    private void progressImage(String url, GlideImageView imageview) {
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
//                Log.i("percent", percent + "");
//                Log.i("isDone", isDone + "");
//                mProgressView.setProgress(percent);
//                mProgressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
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
                Log.i("percent", percent + "");
                Log.i("isDone", isDone + "");
                mProgressView.setProgress(percent);
                mProgressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });
        imageLoader.requestBuilder(url, options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageview);
    }
}

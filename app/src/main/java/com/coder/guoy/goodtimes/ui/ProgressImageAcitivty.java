package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.progress.CircleProgressView;
import com.coder.guoy.goodtimes.progress.GlideImageView;
import com.coder.guoy.goodtimes.utils.GlideUtils;

public class ProgressImageAcitivty extends AppCompatActivity {

    private CircleProgressView mProgressView;
    private GlideImageView glideImageView;
    //    private String url = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat.jpg";
    private String url = "https://raw.githubusercontent.com/sfsheng0322/GlideImageView/master/screenshot/cat_thumbnail.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_image);
        mProgressView = findViewById(R.id.circleprogressview);
        glideImageView = findViewById(R.id.glideimageview);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideUtils.progressImage(url, glideImageView, mProgressView);
            }
        });
    }

}

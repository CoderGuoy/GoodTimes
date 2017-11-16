package com.coder.guoy.goodtimes.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.databinding.ActivityBaseMvvmBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.utils.NetUtils;

import rx.Subscription;


/**
 * @Version:1.0
 * @Author:Guoy
 * @CreateTime:2017年4月7日
 * @Descrpiton:MVVM模式的BaseActivity
 */

public class MvvmBaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {
    protected SV bindingView;// 布局view
    private ImageView imgProgress;//努力加载中...
    private View refresh;//加载失败
    private ActivityBaseMvvmBinding mBaseBinding;
    private Animatable mAnimationDrawable;
    protected Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base_mvvm, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        //content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        // 设置透明状态栏
//        StatusBarUtils.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        // 加载动画
        imgProgress = findViewById(R.id.img_progress);
        refresh = findViewById(R.id.ll_error_refresh);

        mAnimationDrawable = (Animatable) imgProgress.getDrawable();

        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        // 点击加载失败布局
        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        bindingView.getRoot().setVisibility(View.GONE);
        onRefresh();
    }

    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {
        if (!NetUtils.isNetworkConnected(getApplicationContext())) {//没有网络
            showError();
        } else {
            showLoading();
            getData();
        }
    }

    /**
     * 进行网络请求时，不用重写showContentView
     * 不进行网络请求时，需要手动添加showContentView
     */
    protected void getData() {
    }

    /**
     * 显示进度条
     */
    protected void showLoading() {
        if (imgProgress.getVisibility() != View.VISIBLE) {
            imgProgress.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (imgProgress.getVisibility() != View.GONE) {
            imgProgress.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (imgProgress.getVisibility() != View.GONE) {
            imgProgress.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    /**
     * 添加fragment
     *
     * @param addId
     * @param fragment
     */
    protected void addFragment(int addId, MvvmBaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().add(addId, fragment).commit();
    }

    protected void replaceFragment(int addId, MvvmBaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(addId, fragment).commit();
    }

    protected void removeFragment(MvvmBaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
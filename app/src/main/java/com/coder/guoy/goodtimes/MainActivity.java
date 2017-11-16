package com.coder.guoy.goodtimes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.coder.guoy.goodtimes.api.ApiHelper;
import com.coder.guoy.goodtimes.api.GirlHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.cache.CacheActivity;
import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.ui.AnimatedActivity;
import com.coder.guoy.goodtimes.ui.DataActivity;
import com.coder.guoy.goodtimes.ui.ProgressImageAcitivty;
import com.coder.guoy.goodtimes.ui.girl.GirlActivity;
import com.coder.guoy.goodtimes.ui.girl.GirlAdapter;
import com.coder.guoy.goodtimes.utils.GlideUtils;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private GridLayoutManager mLayoutManager;
    private GirlAdapter adapter;
    private int color;
    private int PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getBannerMMData(Constants.WALLPAPER,1);
        getNetData(Constants.NEW, PAGE);
    }

    private void initView() {
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageMenu.setOnClickListener(this);
        initRecyclerView(binding.recyclerviewModel1);
    }

    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    // TODO: 为Banner图获取网络图片
    private void getBannerMMData(String url,int page){
        GirlHelper.GirlHelper(url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        String imageUrl = null;
                        //随即取集合内的一张图
                        int position = (int) (Math.random() * beanList.size());
                        if (beanList.get(position).getImageUrl() != null) {
                            imageUrl = beanList.get(position).getImageUrl();
                            Log.i("Banner_imageUrl", imageUrl);
                        }
                        GlideUtils.setImage(imageUrl, binding.imageHome);
                        downloadPic(imageUrl);
                        initDrawerlayout(imageUrl);
                    }
                });
    }

    //TODO: 获取网络数据
    private void getNetData(String url, int page) {
        GirlHelper.GirlHelper(url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        adapter.setNewData(beanList);
                    }
                });
    }

    // TODO: 初始化侧拉菜单
    private void initDrawerlayout(String url) {
        View headerView = binding.navigationview.getHeaderView(0);
        NavigationHeaderBinding bind = DataBindingUtil.bind(headerView);
        GlideUtils.setImage(url, bind.imageHead);
        bind.llNav1.setOnClickListener(listener);
        bind.llNav2.setOnClickListener(listener);
        bind.llNav3.setOnClickListener(listener);
        bind.llNav4.setOnClickListener(listener);
        bind.llNav5.setOnClickListener(listener);
    }

    // TODO: 将输入流解码为位图
    private void downloadPic(String url) {
        //截取解析的URL地址，拼接后再使用
        String picName = url.substring(20, url.length());
        ApiHelper.getInstance(Constants.MM_URL).downloadPic(picName)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        getBitmapColor(bitmap);
                    }
                });
    }

    // TODO: Palette从图片(Bitmap)中提取颜色
    private void getBitmapColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    //获取有活力的 颜色
                    color = vibrantSwatch.getRgb();
                } else {
                    //都没获取到，用默认颜色
                    color = Color.rgb(229, 67, 124);//#E5437C
                }
                //设置幕布颜色
                binding.collapsingtollbar.setContentScrimColor(color);
                //设置模块文字颜色
                binding.textModel1.setTextColor(color);
            }
        });
    }

    // TODO: 图片列表
    private void initRecyclerView(RecyclerView recyclerView) {
        adapter = new GirlAdapter(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isFadeTips() == false
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                PAGE++;
                upNetData(Constants.NEW, PAGE);
            }
        }
    };

    private void upNetData(String url, int page) {
        GirlHelper.GirlHelper(url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        if (beanList.size() > 0 && beanList != null) {
                            adapter.addData(beanList, true);
                        } else {
                            adapter.addData(null, false);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_menu:// 右侧功能菜单
                ToastUtil.show("弹出菜单，未实现");
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
                        case R.id.ll_nav_1:
                            startActivity(new Intent(MainActivity.this, CacheActivity.class));
                            break;
                        case R.id.ll_nav_2:
                            startActivity(new Intent(MainActivity.this, GirlActivity.class));
                            break;
                        case R.id.ll_nav_3:
                            startActivity(new Intent(MainActivity.this, ProgressImageAcitivty.class));
                            break;
                        case R.id.ll_nav_4:
                            startActivity(new Intent(MainActivity.this, DataActivity.class));
                            break;
                        case R.id.ll_nav_5:
                            startActivity(new Intent(MainActivity.this, AnimatedActivity.class));
                            break;
                    }
                }
            }, 260);
        }
    };
}
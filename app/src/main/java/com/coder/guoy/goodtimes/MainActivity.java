package com.coder.guoy.goodtimes;

import android.content.Context;
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
import com.coder.guoy.goodtimes.ui.DataActivity;
import com.coder.guoy.goodtimes.ui.ProgressImageAcitivty;
import com.coder.guoy.goodtimes.ui.home.HomeImageAdapter;
import com.coder.guoy.goodtimes.ui.home.ImageTypeAcitvity;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private GridLayoutManager mLayoutManager;
    private HomeImageAdapter imageAdapter;//图片列表
    private int color;
    private int PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getBannerMMData(Constants.MM_URL, Constants.WALLPAPER, 1);
        getNetData(Constants.MM_URL, Constants.NEW, PAGE);
    }

    private void initView() {
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageMenu.setOnClickListener(this);
        binding.cardviewType1.setOnClickListener(this);
        binding.cardviewType2.setOnClickListener(this);
        binding.cardviewType3.setOnClickListener(this);
        binding.cardviewType4.setOnClickListener(this);
        binding.cardviewType5.setOnClickListener(this);
        binding.cardviewType6.setOnClickListener(this);
        binding.cardviewType7.setOnClickListener(this);
        binding.cardviewType8.setOnClickListener(this);
        binding.cardviewType9.setOnClickListener(this);
        binding.cardviewType10.setOnClickListener(this);
        binding.cardviewMore.setOnClickListener(this);
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
    private void getBannerMMData(String baseUrl, String url, int page) {
        GirlHelper.ImageHelper(baseUrl, url, page)
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
    private void getNetData(String baseUrl, String url, int page) {
        GirlHelper.ImageHelper(baseUrl, url, page)
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
                        imageAdapter.setNewData(beanList);
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
                setColor(color);
                imageAdapter.setColor(color);
            }
        });
    }

    private void setColor(int color) {
        //幕布颜色
        binding.collapsingtollbar.setContentScrimColor(color);
        //文字颜色
        binding.textModel1.setTextColor(color);
        binding.textLeft.setBackgroundColor(color);
        binding.textMore.setBackgroundColor(color);
        //分类标题
        binding.textType1.setBackgroundColor(color);
        binding.textType2.setBackgroundColor(color);
        binding.textType3.setBackgroundColor(color);
        binding.textType4.setBackgroundColor(color);
        binding.textType5.setBackgroundColor(color);
        binding.textType6.setBackgroundColor(color);
        binding.textType7.setBackgroundColor(color);
        binding.textType8.setBackgroundColor(color);
        binding.textType9.setBackgroundColor(color);
        binding.textType10.setBackgroundColor(color);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        imageAdapter = new HomeImageAdapter(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_menu:// 右侧功能菜单
                getBannerMMData(Constants.MM_URL, Constants.WALLPAPER, 1);
                break;
            case R.id.cardview_type1:
                startImageType(this, Constants.MM_URL, Constants.XINGGAN, "性感美女");
                break;
            case R.id.cardview_type2:
                startImageType(this, Constants.MM_URL, Constants.SHAONV, "少女萝莉");
                break;
            case R.id.cardview_type3:
                startImageType(this, Constants.MM_URL, Constants.MRXT, "美乳香臀");
                break;
            case R.id.cardview_type4:
                startImageType(this, Constants.MM_URL, Constants.SWMT, "丝袜美腿");
                break;
            case R.id.cardview_type5:
                startImageType(this, Constants.MM_URL, Constants.XGTX, "性感特写");
                break;
            case R.id.cardview_type6:
                startImageType(this, Constants.MM_URL, Constants.OUMEI, "欧美女神");
                break;
            case R.id.cardview_type7:
                startImageType(this, Constants.MM_URL, Constants.COLLECTION, "女神集合");
                break;
            case R.id.cardview_type8:
                startImageType(this, Constants.GG_URL, Constants.JRMN, "肌肉猛男");
                break;
            case R.id.cardview_type9:
                startImageType(this, Constants.GG_URL, Constants.MLXN, "魅力型男");
                break;
            case R.id.cardview_type10:
                startImageType(this, Constants.GG_URL, Constants.HMXR, "花美鲜肉");
                break;
            case R.id.cardview_more:
                startImageType(this, Constants.MM_URL, Constants.NEW, "最新美图");
                break;
        }
    }

    public void startImageType(Context context, String baseUrl, String url, String title) {
        Intent intent = new Intent(context, ImageTypeAcitvity.class);
        intent.putExtra("baseUrl", baseUrl);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("color", color);
        context.startActivity(intent);
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
                            break;
                        case R.id.ll_nav_3:
                            startActivity(new Intent(MainActivity.this, ProgressImageAcitivty.class));
                            break;
                        case R.id.ll_nav_4:
                            startActivity(new Intent(MainActivity.this, DataActivity.class));
                            break;
                        case R.id.ll_nav_5:
                            break;
                    }
                }
            }, 260);
        }
    };

}
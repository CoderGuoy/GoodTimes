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
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.cache.CacheActivity;
import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.ui.DataActivity;
import com.coder.guoy.goodtimes.ui.ProgressImageAcitivty;
import com.coder.guoy.goodtimes.ui.TypePage1Activity;
import com.coder.guoy.goodtimes.ui.adapter.HomePageAdapter;
import com.coder.guoy.goodtimes.ui.adapter.HomeTypeAdapter;
import com.coder.guoy.goodtimes.utils.GlideUtils;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private int[] images = {R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1,};
    private String[] titles = {"分类", "分类", "分类", "分类", "分类",
            "分类", "分类", "分类", "分类", "分类",};
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getBannerNetData(Constants.ZMBZ_KTDM);
        getNetData(Constants.ZOL_FJ, initRecyclerView(binding.recyclerviewModel1));
        getNetData(Constants.ZOL_MV, initRecyclerView(binding.recyclerviewModel2));
        getNetData(Constants.ZOL_DM, initRecyclerView(binding.recyclerviewModel3));
        getNetData(Constants.ZOL_CY, initRecyclerView(binding.recyclerviewModel4));
        getNetData(Constants.ZOL_AQ, initRecyclerView(binding.recyclerviewModel5));
        getNetData(Constants.ZOL_KT, initRecyclerView(binding.recyclerviewModel6));
        getNetData(Constants.ZOL_KA, initRecyclerView(binding.recyclerviewModel7));
        getNetData(Constants.ZOL_MX, initRecyclerView(binding.recyclerviewModel8));
        getNetData(Constants.ZOL_YX, initRecyclerView(binding.recyclerviewModel9));
        getNetData(Constants.ZOL_CM, initRecyclerView(binding.recyclerviewModel10));
        getNetData(Constants.ZOL_TY, initRecyclerView(binding.recyclerviewModel11));
        getNetData(Constants.ZOL_JR, initRecyclerView(binding.recyclerviewModel12));
        getNetData(Constants.ZOL_YS, initRecyclerView(binding.recyclerviewModel13));
        getNetData(Constants.ZOL_JZ, initRecyclerView(binding.recyclerviewModel14));
        getNetData(Constants.ZOL_DW, initRecyclerView(binding.recyclerviewModel15));
        getNetData(Constants.ZOL_ZW, initRecyclerView(binding.recyclerviewModel16));
    }

    private void initView() {
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageMenu.setOnClickListener(this);
        initRecyclerView(images, titles, binding.recyclerviewHomeType);
    }

    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    // TODO: 为Banner图获取网络图片
    private void getBannerNetData(final String url) {
        Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements main_cont = document.getElementsByClass("main_cont");
                    Document parse = Jsoup.parse(main_cont.toString());
                    Element imageLists = parse.getElementsByClass("list_cont Left_list_cont").get(0);
                    Elements li = imageLists.select("li");
                    for (Element imageList : li) {
                        //详细页连接
                        String linkUrl = imageList.select("a").first().attr("href");
                        if (!linkUrl.startsWith(Constants.BASE_URl)) {
                            linkUrl = Constants.BASE_URl + linkUrl.substring(1);
                        }
                        //图片标题
                        String imgaeTitle = imageList.select("p").text();

                        Document document2 = Jsoup.connect(linkUrl).get();
                        Elements main_cont2 = document2.getElementsByClass("pic_main");
                        Document parse2 = Jsoup.parse(main_cont2.toString());
                        Elements imageLists2 = parse2.getElementsByClass("pic-meinv");
                        //图片地址
                        String imgUrl = imageLists2.select("img").first().attr("src");

                        list.add(new ImageBean(linkUrl, imgUrl, imgaeTitle));
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
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

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError:BannerNetData:", e.toString());
                    }

                });
    }

    //TODO: 获取网络数据
    private void getNetData(final String url, final HomePageAdapter adapter) {
        Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements li = document.getElementsByClass("photo-list-padding");
                    for (Element imageList : li) {
                        //详细页连接
                        String linkUrl = imageList.select("a").attr("href");
                        if (!linkUrl.startsWith(Constants.ZOL_URl)) {
                            linkUrl = Constants.ZOL_URl + linkUrl.substring(1);
                        }
                        //图片标题
                        String imageTitle = imageList.select("img").attr("title");

                        //原图
                        Document document2 = Jsoup.connect(linkUrl).get();
                        Elements wrapper = document2.getElementsByClass("wrapper mt15");
                        String wrapperUrl = wrapper.select("a").get(1).attr("href");
                        if (!wrapperUrl.startsWith(Constants.ZOL_URl)) {
                            wrapperUrl = Constants.ZOL_URl + wrapperUrl.substring(1);
                        }
                        Document document3 = Jsoup.connect(wrapperUrl).get();
                        String imgUrl = document3.select("img").first().attr("src");

                        list.add(new ImageBean(wrapperUrl, imgUrl, imageTitle));
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        adapter.setNewData(beanList);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.toString());
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
        String picName = url.substring(24, url.length());
        ApiHelper.getInstance(Constants.IMG_URl).downloadPic(picName)
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
                    color = Color.rgb(135, 206, 235);//#87CEEB
                }
                binding.collapsingtollbar.setContentScrimColor(color);
                setTextColor(color);
            }
        });
    }

    // TODO: 首页分类列表
    private void initRecyclerView(int[] images, String[] titles, RecyclerView recyclerView) {
        HomeTypeAdapter adapter = new HomeTypeAdapter(this, images, titles, 10);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    // TODO: 图片列表
    private HomePageAdapter initRecyclerView(RecyclerView recyclerView) {
        HomePageAdapter adapter = new HomePageAdapter(this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return adapter;
    }

    private void setTextColor(int color) {
        binding.textModel1.setTextColor(color);
        binding.textModel1More.setBackgroundColor(color);
        binding.textModel2.setTextColor(color);
        binding.textModel2More.setBackgroundColor(color);
        binding.textModel3.setTextColor(color);
        binding.textModel3More.setBackgroundColor(color);
        binding.textModel4.setTextColor(color);
        binding.textModel4More.setBackgroundColor(color);
        binding.textModel5.setTextColor(color);
        binding.textModel5More.setBackgroundColor(color);
        binding.textModel6.setTextColor(color);
        binding.textModel6More.setBackgroundColor(color);
        binding.textModel7.setTextColor(color);
        binding.textModel7More.setBackgroundColor(color);
        binding.textModel8.setTextColor(color);
        binding.textModel8More.setBackgroundColor(color);
        binding.textModel9.setTextColor(color);
        binding.textModel9More.setBackgroundColor(color);
        binding.textModel10.setTextColor(color);
        binding.textModel10More.setBackgroundColor(color);
        binding.textModel11.setTextColor(color);
        binding.textModel11More.setBackgroundColor(color);
        binding.textModel12.setTextColor(color);
        binding.textModel12More.setBackgroundColor(color);
        binding.textModel13.setTextColor(color);
        binding.textModel13More.setBackgroundColor(color);
        binding.textModel14.setTextColor(color);
        binding.textModel14More.setBackgroundColor(color);
        binding.textModel15.setTextColor(color);
        binding.textModel15More.setBackgroundColor(color);
        binding.textModel16.setTextColor(color);
        binding.textModel16More.setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.image_menu:// 右侧功能菜单
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
                            startActivity(new Intent(MainActivity.this, TypePage1Activity.class));
                            break;
                        case R.id.ll_nav_3:
                            startActivity(new Intent(MainActivity.this, ProgressImageAcitivty.class));
                            break;
                        case R.id.ll_nav_4:
                            startActivity(new Intent(MainActivity.this, DataActivity.class));
                            break;
                        case R.id.ll_nav_5:
                            ToastUtil.show(getString(R.string.pleasewait));
                            break;
                    }
                }
            }, 260);
        }
    };
}
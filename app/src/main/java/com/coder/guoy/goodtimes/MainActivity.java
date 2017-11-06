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
import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.ui.CacheActivity;
import com.coder.guoy.goodtimes.ui.TypePage1Activity;
import com.coder.guoy.goodtimes.ui.adapter.HomeTypeAdapter;
import com.coder.guoy.goodtimes.ui.adapter.TypePageAdapter;
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

import static com.coder.guoy.goodtimes.Constants.BASE_URl;
import static com.coder.guoy.goodtimes.Constants.IMG_URl;
import static com.coder.guoy.goodtimes.Constants.ZMBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_JSBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_KTDM;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_QCBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_YXBZ;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private String imageUrl = null;
    private int[] images = {R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1, R.drawable.home_type1, R.drawable.home_type1,
            R.drawable.home_type1,};
    private String[] titles = {"手机壁纸", "壁纸专题", "图片专题", "明星图片", "图片大全",
            "美女图片", "精选合集", "分类1", "分类2", "分类3",};
    private int color;
    private String classType1 = "list_cont list_cont1 w1180";
    private String classType2 = "list_cont list_cont2 w1180";
    private String classType3 = "list_cont Left_list_cont";
    private String imageType1 = "src";
    private String imageType2 = "url";
    private int Page0 = 0;
    private int Page1 = 1;
    private int Page2 = 2;
    private int Page3 = 3;
    private int Page4 = 4;
    private int Page5 = 5;
    private int Page6 = 6;
    //RecyclerView展示条目数量
    private int count_4 = 4;
    private int count_9 = 9;
    //RecyclerView列数
    private int col_2 = 2;
    private int col_3 = 3;
    private TypePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getBannerNetData(ZMBZ_KTDM);
        //最新
        getNetData(ZMBZ, Page0, classType1, imageType1, binding.recyclerviewModel2);
        //游戏
        getNetData(ZMBZ_YXBZ, Page0, classType3, imageType1, binding.recyclerviewModel3);
        //动漫
        getNetData(ZMBZ_KTDM, Page0, classType3, imageType1, binding.recyclerviewModel4);
        //汽车
        getNetData(ZMBZ_QCBZ, Page0, classType3, imageType1, binding.recyclerviewModel6);
        //军事
        getNetData(ZMBZ_JSBZ, Page0, classType3, imageType1, binding.recyclerviewModel5);
    }

    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initView() {
        initRecyclerView(images, titles, binding.recyclerviewHomeType);
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageMenu.setOnClickListener(this);
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
                    Elements imageLists = parse.getElementsByClass("Left_bar");
                    Elements li = imageLists.select("li");
                    for (Element imageList : li) {
                        //详细页连接
                        String linkUrl = imageList.select("a").first().attr("href");

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
                        //随即取集合内的一张图
                        int position = (int) (Math.random() * beanList.size());
                        if (beanList.get(position).getImageUrl() != null) {
                            imageUrl = beanList.get(position).getImageUrl();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        GlideUtils.setImage(imageUrl, binding.imageHome);
                        initDrawerlayout(imageUrl);
                        downloadPic(imageUrl);
                        Log.i("onCompeted", "完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError:BannerNetData:", e.toString());
                    }

                });
    }
    //TODO: 获取网络数据

    /**
     * @param url          连接地址
     * @param position     页面中数据源条目位置
     * @param recyclerView 对应的控件
     */
    private void getNetData(final String url, final int position,
                            final String classType, final String imageType,
                            final RecyclerView recyclerView) {
        Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements main_cont = document.getElementsByClass("main_cont");
                    Document parse = Jsoup.parse(main_cont.toString());
                    Element imageLists = parse.getElementsByClass(classType).get(position);
                    Elements li = imageLists.select("li");
                    for (Element imageList : li) {
                        //详细页连接
                        String linkUrl = imageList.select("a").first().attr("href");
                        if (!linkUrl.startsWith(BASE_URl)) {
                            linkUrl = BASE_URl + linkUrl.substring(1);
                        }
                        //图片标题
                        String imgaeTitle = imageList.select("p").text();

                        Document document2 = Jsoup.connect(linkUrl).get();
                        Elements main_cont2 = document2.getElementsByClass("pic_main");
                        Document parse2 = Jsoup.parse(main_cont2.toString());
                        Elements imageLists2 = parse2.getElementsByClass("pic-meinv");
                        //图片地址
                        String imgUrl = imageLists2.select("img").first().attr(imageType);

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
                        initRecyclerView(beanList, recyclerView);
                    }

                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted", "完成");
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
        ApiHelper.getInstance(IMG_URl).downloadPic(picName)
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
    private void initRecyclerView(List<ImageBean> beanList, RecyclerView recyclerView) {
        adapter = new TypePageAdapter(this, beanList, 4);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setTextColor(int color) {
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
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_4:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_5:
                            ToastUtil.show("敬请期待");
                            break;
                    }
                }
            }, 260);
        }
    };
}
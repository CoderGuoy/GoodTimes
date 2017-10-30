package com.coder.guoy.goodtimes;

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

import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.ui.HomeImageAdapter;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    //最新图片
    private int newPage = 0;
    //影视图片
    private int moviePage = 3;
    //美妆图片
    private int makeUpsPage = 2;
    //节日图片
    private int festivalPage = 1;
    //旅游图片
    private int tourismPage = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getNetData(Constants.HOME, newPage, binding.recyclerviewNew);
        getNetData(Constants.HOME, moviePage, binding.recyclerviewMovie);
        getNetData(Constants.HOME, makeUpsPage, binding.recyclerviewMakeups);
        getNetData(Constants.HOME, festivalPage, binding.recyclerviewFestival);
        getNetData(Constants.HOME, tourismPage, binding.recyclerviewTourism);
    }

    private void initView() {
        initDrawerlayout();
        binding.flTitleMenu.setOnClickListener(this);
        binding.imageHome.setImageResource(R.drawable.model03);
        getBitmapColor();
    }
    //TODO: 获取网络数据

    /**
     * @param url          连接地址
     * @param position     页面中数据源条目位置
     * @param recyclerView 对应的控件
     */
    private void getNetData(final String url, final int position, final RecyclerView recyclerView) {
        Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements main_cont = document.getElementsByClass("main_cont");
                    Document parse = Jsoup.parse(main_cont.toString());
                    Element imageLists = parse.getElementsByClass("list_cont list_cont2 w1180").get(position);
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
                        String imgUrl = imageLists2.select("img").first().attr("url");

                        list.add(new ImageBean(linkUrl, imgUrl, imgaeTitle));
                    }
                    subscriber.onNext(list);
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
                        Log.i("onError", e.toString());
                    }

                });
    }

    // TODO: 初始化RecyclerView  

    /**
     * @param beanList     数据列表
     * @param recyclerView 对应的控件
     */
    private void initRecyclerView(List<ImageBean> beanList, RecyclerView recyclerView) {
        HomeImageAdapter adapter = new HomeImageAdapter(this, beanList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    // TODO: 初始化侧拉菜单
    private void initDrawerlayout() {
        View headerView = binding.navigationview.getHeaderView(0);
        NavigationHeaderBinding bind = DataBindingUtil.bind(headerView);
        bind.llNavVideo.setOnClickListener(listener);
        bind.llNav2.setOnClickListener(listener);
        bind.llNav3.setOnClickListener(listener);
        bind.llNav4.setOnClickListener(listener);
        bind.llNav5.setOnClickListener(listener);
    }

    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    // TODO: Palette从图片(Bitmap)中提取颜色
    private void getBitmapColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.model03);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int color = 0;
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                if (lightVibrantSwatch != null) {
                    //获取有活力的亮色 颜色
                    color = lightVibrantSwatch.getRgb();
                } else {
                    //获取有活力的 颜色
                    color = vibrantSwatch.getRgb();
                }
                binding.collapsingtollbar.setContentScrimColor(color);
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
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
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
                        case R.id.ll_nav_video:
                            ToastUtil.show("敬请期待");
                            break;
                        case R.id.ll_nav_2:
                            ToastUtil.show("敬请期待");
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
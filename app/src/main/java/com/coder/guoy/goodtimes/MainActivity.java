package com.coder.guoy.goodtimes;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;

import com.coder.guoy.goodtimes.api.ApiHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ActivityMainBinding;
import com.coder.guoy.goodtimes.databinding.NavigationHeaderBinding;
import com.coder.guoy.goodtimes.linstener.PerfectClickListener;
import com.coder.guoy.goodtimes.ui.Fragment1;
import com.coder.guoy.goodtimes.ui.Fragment2;
import com.coder.guoy.goodtimes.ui.Fragment3;
import com.coder.guoy.goodtimes.ui.Fragment4;
import com.coder.guoy.goodtimes.utils.GlideUtils;
import com.coder.guoy.goodtimes.utils.RxBus;
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

import static com.coder.guoy.goodtimes.Constants.Home_Color;
import static com.coder.guoy.goodtimes.Constants.IMG_URl;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_KTDM;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private String[] fragmentTitile = {"热门分类", "影视", "明星", "高清", "体育", "美食"};
    private List<Fragment> fragments = new ArrayList<>();
    private String imageUrl = null;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        transparentStatusBar();
        initView();
        getBannerNetData(ZMBZ_KTDM);
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
        initFragment();
        binding.flTitleMenu.setOnClickListener(this);
        binding.viewpager.setAdapter(new FragmentAdapter(this.getSupportFragmentManager()));
        binding.tablayoutMain.setupWithViewPager(binding.viewpager);
    }

    // TODO: 初始化侧拉菜单
    private void initDrawerlayout(String url) {
        View headerView = binding.navigationview.getHeaderView(0);
        NavigationHeaderBinding bind = DataBindingUtil.bind(headerView);
        GlideUtils.setImage(url, bind.imageHead);
        bind.llNavVideo.setOnClickListener(listener);
        bind.llNav2.setOnClickListener(listener);
        bind.llNav3.setOnClickListener(listener);
        bind.llNav4.setOnClickListener(listener);
        bind.llNav5.setOnClickListener(listener);
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
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                if (vibrantSwatch != null) {
                    //获取有活力的 颜色
                    color = vibrantSwatch.getRgb();
                } else if (lightVibrantSwatch != null) {
                    //获取有活力的亮色 颜色
                    color = lightVibrantSwatch.getRgb();
                } else {
                    //都没获取到，用默认颜色
                    color = Color.rgb(63, 81, 181);
                }
                binding.collapsingtollbar.setContentScrimColor(color);
                RxBus.getDefault().post(Home_Color,color);
            }
        });
    }

    // TODO: 初始化Fragment
    private void initFragment() {
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        fragments.add(new Fragment4());
        fragments.add(new Fragment4());
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitile[position];
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:// 开启菜单
                binding.drawerlayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
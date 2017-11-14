package com.coder.guoy.goodtimes.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.Fragment4Binding;
import com.coder.guoy.goodtimes.ui.home.HomePageAdapter;

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

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:图片详情页
 */
public class DataActivity extends AppCompatActivity {
    private Fragment4Binding bindingView;
    private long startTime = 0;
    private String zol = "photo-list-padding";
    private String wmpic = "item_box";
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, R.layout.fragment_4);
        initRecyclerView(bindingView.recyclerviewModel2);

        getNetData(Constants.WMPIC_URl + "meinv/", zol);
    }

    //TODO: 获取网络数据
    private void getNetData(final String url, final String classType) {
        startTime = System.currentTimeMillis();
        Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements li = document.getElementsByClass(classType);
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
                        Elements main_cont2 = document2.getElementsByClass("photo");
                        String imgUrl = main_cont2.select("img").first().attr("src");

                        list.add(new ImageBean(linkUrl, imgUrl,imageTitle));
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
                        int time = (int) (System.currentTimeMillis() - startTime);
                        Log.i("time", time + "ms");
                        adapter.setNewData(beanList);
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

    // TODO: 初始化RecyclerView

    /**
     * @param recyclerView 对应的控件
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        adapter = new HomePageAdapter(this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}

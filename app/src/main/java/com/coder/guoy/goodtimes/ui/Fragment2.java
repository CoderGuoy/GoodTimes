package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.GirlBean;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.Fragment2Binding;

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


public class Fragment2 extends MvvmBaseFragment<Fragment2Binding> {
    private List<GirlBean> mList = new ArrayList<>();
    private StaggeredGridLayoutManager mLayoutManager;
    private GirlAdapter adapter;

    @Override
    public int setContent() {
        return R.layout.fragment_2;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void getData() {
        super.getData();
        OpenSex();
    }

    private void OpenSex() {
        final Observable<List<GirlBean>> observable = Observable.create(new Observable.OnSubscribe<List<GirlBean>>() {
            @Override
            public void call(Subscriber<? super List<GirlBean>> subscriber) {
                List<GirlBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(Constants.XX_URL).get();
                    //解析方式一
//                    Element imageList = document.getElementById("blog-grid");
//                    Elements imageLists = imageList.getElementsByAttributeValueContaining("class",
//                            "col-lg-4 col-md-4 three-columns post-box");
                    //解析方式二
                    Elements imageLists = document.getElementsByClass("site-main");
                    for (Element imageList : imageLists) {
//                        Element link = imageList.select("a[href]").first();
//                        Element img = imageList.select("img").first();
//                        String linkUrl = link.attr("abs:href");
//                        //图片地址
//                        String imgUrl = img.attr("abs:src");
//                        //图片标题
                        String imgaeTitle = imageList.select(".entry-content").text();
                        list.add(new GirlBean("", "", imgaeTitle));
                    }
                    subscriber.onNext(list);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

        Subscriber<List<GirlBean>> subscriber = new Subscriber<List<GirlBean>>() {
            @Override
            public void onNext(List<GirlBean> beanList) {
                Log.i("beanListLink2", beanList.get(0).getLinkUrl());
                Log.i("beanListImage2", beanList.get(0).getImageUrl());
                Log.i("beanListTitle2", beanList.get(0).getImgaeTitle());
                mList = beanList;
                initRecyclerView();
                showContentView();
            }

            @Override
            public void onCompleted() {
                Log.i("onCompleted", "完成");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("onError", "home:" + e.toString());
            }

        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 初始化RecyclerView的Adapter
    private void initRecyclerView() {
        adapter = new GirlAdapter(getContext(), mList);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bindingView.recyclerviewList2.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewList2.setAdapter(adapter);
    }
}

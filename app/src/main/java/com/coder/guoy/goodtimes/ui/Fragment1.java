package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.GirlBean;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.FragmentHomeBinding;

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


public class Fragment1 extends MvvmBaseFragment<FragmentHomeBinding> {
    private List<GirlBean> mList = new ArrayList<>();
    private StaggeredGridLayoutManager mLayoutManager;
    private GirlAdapter adapter;

    @Override
    public int setContent() {
        return R.layout.fragment_home;
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
                    Document document = Jsoup.connect(Constants.MM_URL + Constants.NEW + 1).get();
                    Element imageList = document.getElementById("blog-grid");
                    Elements imageLists = imageList.getElementsByAttributeValueContaining("class",
                            "col-lg-4 col-md-4 three-columns post-box");
                    for (Element imagelist : imageLists) {
                        Element link = imagelist.select("a[href]").first();
                        Element img = imagelist.select("img").first();
                        String linkUrl = link.attr("abs:href");
                        String imgUrl = img.attr("abs:src");
                        String imgaeTitle = link.attr(".local-link");
                        Log.i("beanListTitle_E", imgaeTitle);
                        list.add(new GirlBean(linkUrl, imgUrl, imgaeTitle));
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
                Log.i("beanListLink", beanList.get(0).getLinkUrl());
                Log.i("beanListImage", beanList.get(0).getImageUrl());
                Log.i("beanListTitle", beanList.get(0).getImgaeTitle());
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
                Log.i("onError", e.toString());
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
        bindingView.recyclerviewList.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewList.setAdapter(adapter);
    }
}

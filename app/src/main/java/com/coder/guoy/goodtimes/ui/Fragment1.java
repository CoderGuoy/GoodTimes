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
    private int page = 1;

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
        OpenSex(page);
    }

    private void OpenSex(final int page) {
        final Observable<List<GirlBean>> observable = Observable.create(new Observable.OnSubscribe<List<GirlBean>>() {
            @Override
            public void call(Subscriber<? super List<GirlBean>> subscriber) {
                List<GirlBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(Constants.MOB_URl).get();
                    Elements main_cont = document.getElementsByClass("main_cont");
                    Document parse = Jsoup.parse(main_cont.toString());
//                    Elements imageLists = parse.getElementsByClass("pic-meinv");
                    Elements imageLists = parse.getElementsByClass("scroll-img-cont scroll-img-cont02");
                    Document leftBar = Jsoup.parse(imageLists.toString());
                    Elements li = leftBar.select("li");
                    for (Element imageList : li) {
                        //详细页连接
                        String linkUrl = imageList.select("a").first().attr("href");
                        //图片地址
                        String imgUrl = imageList.select("img").first().attr("src");
                        //图片标题
                        String imgaeTitle = imageList.select("p").text();
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
                Log.i("linkUrl", beanList.get(0).getLinkUrl());
                Log.i("imgUrl", beanList.get(0).getImageUrl());
                Log.i("imgaeTitle", beanList.get(0).getImgaeTitle());
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

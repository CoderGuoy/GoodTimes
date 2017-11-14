package com.coder.guoy.goodtimes.ui.girl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.FragmentGirlBinding;

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


public class FragmentGirl2 extends MvvmBaseFragment<FragmentGirlBinding> {
    private StaggeredGridLayoutManager mLayoutManager;
    private GirlAdapter adapter;

    @Override
    public int setContent() {
        return R.layout.fragment_girl;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    @Override
    protected void getData() {
        super.getData();
        OpenSex();
    }

    private void OpenSex() {
        final Observable<List<ImageBean>> observable = Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(Constants.MM_URL + Constants.XINGGAN + 1).get();
                    Elements imageLists = document.getElementsByClass("col-lg-4 col-md-4 three-columns post-box");
                    for (Element imageList : imageLists) {
                        String linkUrl = imageList.select("a").first().attr("href");
                        //图片地址
                        String imgUrl = imageList.select("img").first().attr("src");
                        //图片标题
                        String imgaeTitle = imageList.select(".entry-title").text();
                        list.add(new ImageBean(linkUrl, imgUrl, imgaeTitle));
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

        Subscriber<List<ImageBean>> subscriber = new Subscriber<List<ImageBean>>() {
            @Override
            public void onNext(List<ImageBean> beanList) {
                adapter.setNewData(beanList);
            }

            @Override
            public void onCompleted() {
                showContentView();
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
        adapter = new GirlAdapter(getContext());
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bindingView.recyclerviewList3.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewList3.setAdapter(adapter);
    }
}
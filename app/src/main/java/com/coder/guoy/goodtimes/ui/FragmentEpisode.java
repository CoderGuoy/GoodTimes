package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.EpisodeBean;
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


public class FragmentEpisode extends MvvmBaseFragment<Fragment2Binding> {
    private List<EpisodeBean> mList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private EpisodeAdapter adapter;

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
        final Observable<List<EpisodeBean>> observable = Observable.create(new Observable.OnSubscribe<List<EpisodeBean>>() {
            @Override
            public void call(Subscriber<? super List<EpisodeBean>> subscriber) {
                List<EpisodeBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(Constants.XX_URL).get();
                    Elements mainLists = document.getElementsByClass("site-main");
                    Elements articleLists = mainLists.select("article");
                    for (Element imageList : articleLists) {
                        String image = imageList.select("img").first().attr("abs:src");
                        String author = imageList.select(".entry-author").text();
                        String time = imageList.select(".entry-date").text();
                        String title = imageList.select(".entry-title").text();
                        String content = imageList.select(".entry-content").select("p").first().text();
                        //评论
//                        String commnet = imageList.select("blockquote").select("p").first().text();
                        list.add(new EpisodeBean(image, author, title, time, content, ""));
                    }
                    subscriber.onNext(list);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

        Subscriber<List<EpisodeBean>> subscriber = new Subscriber<List<EpisodeBean>>() {
            @Override
            public void onNext(List<EpisodeBean> beanList) {
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
        adapter = new EpisodeAdapter(getContext(), mList);
        mLayoutManager = new LinearLayoutManager(getContext());
        bindingView.recyclerviewList2.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewList2.setAdapter(adapter);
    }
}

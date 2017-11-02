package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
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

import static com.coder.guoy.goodtimes.Constants.BASE_URl;
import static com.coder.guoy.goodtimes.Constants.ZMBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_JSBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_KTDM;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_QCBZ;
import static com.coder.guoy.goodtimes.Constants.ZMBZ_YXBZ;


public class Fragment1 extends MvvmBaseFragment<FragmentHomeBinding> {
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
        //精彩推荐
        getNetData(BASE_URl, Page0, classType1, imageType1, bindingView.recyclerviewModel1);
        //最新
        getNetData(ZMBZ, Page0, classType1, imageType1, bindingView.recyclerviewModel2);
        //游戏
        getNetData(ZMBZ_YXBZ, Page0, classType3, imageType1, bindingView.recyclerviewModel3);
        //卡通
        getNetData(ZMBZ_KTDM, Page0, classType3, imageType1, bindingView.recyclerviewModel4);
        //军事
        getNetData(ZMBZ_JSBZ, Page0, classType3, imageType1, bindingView.recyclerviewModel5);
        //汽车
        getNetData(ZMBZ_QCBZ, Page0, classType3, imageType1, bindingView.recyclerviewModel6);
    }

    //TODO: 获取网络数据

    /**
     * @param url          连接地址
     * @param position     页面中数据源条目位置
     * @param recyclerView 对应的控件
     */
    private void getNetData(final String url, final int position, final String classType,
                            final String imageType,
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
//                        for (ImageBean lists : beanList) {
//                            Log.i("LinkUrl", lists.getLinkUrl());
//                            Log.i("ImageUrl", lists.getImageUrl());
//                            Log.i("ImgaeTitle", lists.getImgaeTitle());
//                        }
                        initRecyclerView(beanList, recyclerView);
                    }

                    @Override
                    public void onCompleted() {
                        showContentView();
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
     * @param beanList     数据列表
     * @param recyclerView 对应的控件
     */
    private void initRecyclerView(List<ImageBean> beanList, RecyclerView recyclerView) {
        TypePageAdapter adapter = new TypePageAdapter(getContext(), beanList, 4);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
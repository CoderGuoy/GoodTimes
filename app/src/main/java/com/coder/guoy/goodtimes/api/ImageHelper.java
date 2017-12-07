package com.coder.guoy.goodtimes.api;

import com.coder.guoy.goodtimes.api.bean.ImageBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * @Version:
 * @Author:Guoy
 * @CreateTime:2017/11/14
 * @Descrpiton:
 */
public class ImageHelper {

    /**
     * 美女图片列表
     *
     * @param baseUrl
     * @param url
     * @param page
     * @return
     */
    public static Observable<List<ImageBean>> ImageHelper(final String baseUrl, final String url,
                                                          final int page) {
        return Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(baseUrl + url + page).get();
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
    }

    /**
     * 福利社图片列表
     *
     * @param baseUrl
     * @param url
     * @param page
     * @return
     */
    public static Observable<List<ImageBean>> NvShenHelper(final String baseUrl, final String url,
                                                           final int page) {
        return Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(baseUrl + url + "&page=" + page).get();
                    Elements imageLists = document.getElementsByClass("listbox");
                    for (Element imageList : imageLists) {
                        String linkUrl = imageList.select("a").first().attr("href");
                        //图片地址
                        String imgUrl = imageList.select("img").first().attr("src");
                        //图片标题
                        String imgaeTitle = imageList.select("img").first().attr("alt");
                        list.add(new ImageBean(linkUrl, imgUrl, imgaeTitle));
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 美女图片列表
     *
     * @param url
     * @return
     */
    public static Observable<List<ImageBean>> ImageDetailHelper(final String url) {
        return Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements content = document.getElementsByClass("entry-content");
                    Document document1 = Jsoup.parse(content.toString());
                    Elements gallery = document1.getElementsByClass("rgg-imagegrid gallery");
                    Elements imageLists = gallery.select("a");
                    for (Element imageList : imageLists) {
                        //图片地址
                        String imgUrl = imageList.select("a").first().attr("href");
                        String linkUrl = "";
                        String imgaeTitle = "";
                        list.add(new ImageBean(linkUrl, imgUrl, imgaeTitle));
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}

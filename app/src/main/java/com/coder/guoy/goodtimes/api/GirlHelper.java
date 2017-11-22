package com.coder.guoy.goodtimes.api;

import com.coder.guoy.goodtimes.Constants;
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
public class GirlHelper {

    public static Observable GirlHelper(final String url, final int page) {
        return Observable.create(new Observable.OnSubscribe<List<ImageBean>>() {
            @Override
            public void call(Subscriber<? super List<ImageBean>> subscriber) {
                List<ImageBean> list = new ArrayList<>();
                try {
                    Document document = Jsoup.connect(Constants.MM_URL + url + page).get();
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
}

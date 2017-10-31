package com.coder.guoy.goodtimes.api;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @Version:V1.0
 * @Author:Guoy
 * @CreateTime:2017年10月31日
 * @Descrpiton:网络请求接口
 */
public interface ApiServices {
    // TODO: 下载图片
    @Streaming
    @GET
    Observable<ResponseBody> downloadPic(@Url String url);
}
